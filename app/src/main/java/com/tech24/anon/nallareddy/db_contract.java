package com.tech24.anon.nallareddy;

import android.provider.BaseColumns;

public final class db_contract implements BaseColumns{

    private db_contract() {}

    public static final class proEntry implements BaseColumns {

        public final static String TABLE_NAME = "products_list";

        public final static String _ID = BaseColumns._ID;

        public final static String PRO_NAME ="name";

        public final static String PRO_CAT = "category";

        public final static String PRO_ACT_PRICE = "actual_price";

        public final static String PRO_SEL_PRICE = "selling_price";



    }
}
