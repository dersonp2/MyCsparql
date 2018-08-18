package Model;

import java.util.UUID;

/**
 * Created by Anderson on 11/06/2018.
 */

public class Query {
    private String query;
    private Boolean continuos;
    private String publisherID;
    final String returnCode = UUID.randomUUID().toString();
    private String var1, var2 = null;

    public Query(String query, Boolean continuos, String publisherID, String var1, String var2) {
        this.query = query;
        this.continuos = continuos;
        this.publisherID = publisherID;
        this.var1 = var1;
        this.var2 = var2;
    }

    public static class Builder {
        private String query;
        private Boolean continuos;
        private String publisherID;
        final String returnCode = UUID.randomUUID().toString();
        private String var1, var2;

        public Builder() {

        }

        public Builder query(String query) {
            this.query = query;
            return this;
        }

        public Builder continuos(Boolean continuos) {
            this.continuos = continuos;
            return this;
        }

        public Builder publisherID(String publisherID) {
            this.publisherID = publisherID;
            return this;
        }

        public Builder putStaticNamedModel(String var1, String var2) {
            this.var1 = var1;
            this.var2 = var2;
            return this;
        }

        public Query build() {
            return new Query(query, continuos, publisherID, var1, var2);
        }
    }

    public String getReturnCode() {
        return returnCode;
    }

    public String getPublisherID() {
        return publisherID;
    }

    public Boolean getContinuos() {
        return continuos;
    }

    public String getQuery() {
        return query;
    }

    public String getVar1() {
        return var1;
    }

    public String getVar2() {
        return var2;
    }

}
