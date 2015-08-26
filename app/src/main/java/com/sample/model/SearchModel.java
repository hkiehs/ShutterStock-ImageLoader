package com.sample.model;

import java.util.List;

public class SearchModel {
    public List<Datum> data;

    public class Preview {
        public String url;
    }

    public class Assets {
        public Preview preview;
    }

    public class Datum {
        public Assets assets;
    }
}