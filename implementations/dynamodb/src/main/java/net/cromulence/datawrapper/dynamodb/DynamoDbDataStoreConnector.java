package net.cromulence.datawrapper.dynamodb;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import net.cromulence.datawrapper.document.AbstractDocumentDataStoreConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class DynamoDbDataStoreConnector extends AbstractDocumentDataStoreConnector {

    private static Logger LOG = LoggerFactory.getLogger(DynamoDbDataStoreConnector.class);

    private static final String DEFAULT_OBJECT_ID = "wrapperObject";
    private final AmazonDynamoDB client;
    private final String tableName;

    public DynamoDbDataStoreConnector(String awsAccessKey, String awsSecretKey, Regions region, String tableName) {
        this(awsAccessKey, awsSecretKey, region, tableName, DEFAULT_OBJECT_ID);
    }

    public DynamoDbDataStoreConnector(String awsAccessKey, String awsSecretKey, Regions region, String tableName, String documentId) {
        this(new AWSStaticCredentialsProvider(new AWSCredentials() {

            @Override
            public String getAWSAccessKeyId() {
                return awsAccessKey;
            }

            @Override
            public String getAWSSecretKey() {
                return awsSecretKey;
            }
        }), region, tableName, documentId);
    }

    public DynamoDbDataStoreConnector(AWSCredentialsProvider provider, Regions region, String tableName) {
        this(provider, region, tableName, DEFAULT_OBJECT_ID);
    }

    public DynamoDbDataStoreConnector(AWSCredentialsProvider provider, Regions region, String tableName, String documentId) {
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion(region)
            .withCredentials(provider);

        client = builder.build();

        this.tableName = tableName;
    }

    private Table getTable() {
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(tableName);
        return table;
    }

    @Override
    public void remove(String name) {
        remove(DEFAULT_OBJECT_ID, name);
    }

    @Override
    public void remove(String documentId, String name) {

        Item item = getOrCreateDocument(documentId);
        LOG.debug("remove: removing " + name);
        item.removeAttribute(name);

        LOG.debug("remove: done");
    }

    private Item getOrCreateDocument(String documentId) {
        Item id = getTable().getItem(new KeyAttribute("id", documentId));

        if(id == null) {
            Item i = new Item().withPrimaryKey("id", documentId);
            PutItemOutcome putItemOutcome = getTable().putItem(i);
            id = getTable().getItem(new KeyAttribute("id", documentId));
        }

        return id;
    }

    @Override
    public void put(String name, Object value) {
        put(DEFAULT_OBJECT_ID, name, value);
    }

    @Override
    public void put(String documentId, String name, Object value) {
        Item document = getOrCreateDocument(documentId);

        //if(document.get(name) == null) {

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("id", documentId)
                .withUpdateExpression("set #na = :val1")
                .withNameMap(new NameMap().with("#na", name))
                .withValueMap(new ValueMap().with(":val1", value))
                .withReturnValues(ReturnValue.ALL_NEW);
        //}

        UpdateItemOutcome outcome = getTable().updateItem(updateItemSpec);

    }

    @Override
    public Object get(String name) {
        return get(DEFAULT_OBJECT_ID, name);
    }

    @Override
    public Object get(String documentId, String name) {

        Item document = getOrCreateDocument(documentId);
        return document.get(name);
    }
}
