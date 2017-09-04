package com.student.codemobile.myauthendemo;

import java.util.List;

/**
 * Created by Sitrach on 19/07/2017.
 */

class ProductResult {
    //วิธีการสร้าง Class Gson จากข้อมูล Array ของหน้า Web
    //1.นำ Link Web ไปวางใน Browser แล้ว Copy Code ที่เป็น Array ทั้งหมด
    //2.วาง Cursor ตรงบรรทัดนี้ ใน Class ProductResult
    //3.กดเลือก เมนู Code เลือก Generate แล้วเลือก GsonFormat จากนั้นวางสิ่งที่ Copy เป็น Array ไว้
    //4.แล้วกด OK ระบบจะทำการ Gen โครงสร้างให้ทั้งหมด

    /**
     * products : [{"image":"http://codemobiles.com/adhoc/products/images/p1.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p2.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p3.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p4.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p5.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p6.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p7.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p8.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p9.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p10.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p11.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p12.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p13.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p14.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p15.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p16.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p17.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p18.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p19.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p20.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p21.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p22.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p23.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p24.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"},{"image":"http://codemobiles.com/adhoc/products/images/p25.jpg","title":"Diamon Earrings","price":"20,000.23 บาท"}]
     * error : false
     * error_msg : no
     */

    private boolean error;
    private String error_msg;
    private List<ProductsBean> products;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean {
        /**
         * image : http://codemobiles.com/adhoc/products/images/p1.jpg
         * title : Diamon Earrings
         * price : 20,000.23 บาท
         */

        private String image;
        private String title;
        private String price;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
