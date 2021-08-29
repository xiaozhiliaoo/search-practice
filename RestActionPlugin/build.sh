mvn compile package

bin/elasticsearch-plugin install /yourpath/es/plugins/CustomRestActionPlugin-1.0-SNAPSHOT.zip

curl -XGET 'localhost:9200/_mastering/nodes?pretty'