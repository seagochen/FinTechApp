package com.seagosoft.tws.data;

public class NewsArticleData {

    public int requestId;
    public int articleType;
    public String articleText;

    public NewsArticleData(int requestId, int articleType, String articleText) {
        this.requestId = requestId;
        this.articleType = articleType;
        this.articleText = articleText;
    }
}
