mvn compile package

bin/elasticsearch-plugin install /yourpath/es/plugins/custom-analyzer-5.0.0-SNAPSHOT.zip

POST _analyze
{
  "analyzer": "mastering_analyzer",
  "text": "Hello World"
}