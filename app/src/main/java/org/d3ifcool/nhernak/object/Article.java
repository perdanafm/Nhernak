package org.d3ifcool.nhernak.object;

/**
 * A class for accomodate the list of article that have been input by the admin
 */

public class Article {
    private String mTitleArticle,mIdArticle;
    private String mCategory,mBody;
    private int mImageResourceId;

    public static final class ArticleEntry {
        public static final int CATEGORY_SAPI = 0;
        public static final int CATEGORY_DOMBA = 1;
        public static final int CATEGORY_AYAM = 2;
    }

    /**
     *
     * @param vTitleArticle Variable that represent the title of article
     * @param vCategory Variable that represent category of the article
     * @param vImageResourceId Variable that represent id for the image of article
     */

    public Article(String vIdArticle, String vTitleArticle, String vBody, int vImageResourceId, String vCategory) {
        mTitleArticle = vTitleArticle;
        mCategory = vCategory;
        mBody = vBody;
        mImageResourceId = vImageResourceId;
        mIdArticle = vIdArticle;
    }


    public Article(){}

    public String getmTitleArticle() {
        return mTitleArticle;
    }

    public String getmCategory() {
        return mCategory;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public String getmBody() {
        return mBody;
    }

    public String getmIdArticle() {
        return mIdArticle;
    }
}
