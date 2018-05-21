package khantique.organisation.com.khantique.Adapter;

/**
 * Created by Fujitsu on 21-04-2018.
 */

public class CartPojo {

    String id;
    String p_id;
    String qty;
    String unit_price;
    String total_price;
    String u_id;
    String post_date;
    String post_date_gmt;
    String post_title;
    String post_excerpt;
    String post_status;
    String comment_status;
    String ping_status;
    String post_password;
    String post_name;
    String to_ping;
    String pinged;
    String post_modified;
    String post_modified_gmt;
    String post_content_filtered;
    String post_parent;
    String guid;
    String menu_order;
    String post_type;
    String post_mime_type;
    String comment_count;
    String product_image;

    public CartPojo(String id, String post_title, String unit_price, String qty) {

        this.id=id;
        this.post_title=post_title;
        this.unit_price=unit_price;
        this.qty=qty;

    }

    public CartPojo(String post_title, String unit_price, String qty) {

        this.post_title=post_title;
        this.unit_price=unit_price;
        this.qty=qty;

    }

    public CartPojo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_date_gmt() {
        return post_date_gmt;
    }

    public void setPost_date_gmt(String post_date_gmt) {
        this.post_date_gmt = post_date_gmt;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_excerpt() {
        return post_excerpt;
    }

    public void setPost_excerpt(String post_excerpt) {
        this.post_excerpt = post_excerpt;
    }

    public String getPost_status() {
        return post_status;
    }

    public void setPost_status(String post_status) {
        this.post_status = post_status;
    }

    public String getComment_status() {
        return comment_status;
    }

    public void setComment_status(String comment_status) {
        this.comment_status = comment_status;
    }

    public String getPing_status() {
        return ping_status;
    }

    public void setPing_status(String ping_status) {
        this.ping_status = ping_status;
    }

    public String getPost_password() {
        return post_password;
    }

    public void setPost_password(String post_password) {
        this.post_password = post_password;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public String getTo_ping() {
        return to_ping;
    }

    public void setTo_ping(String to_ping) {
        this.to_ping = to_ping;
    }

    public String getPinged() {
        return pinged;
    }

    public void setPinged(String pinged) {
        this.pinged = pinged;
    }

    public String getPost_modified() {
        return post_modified;
    }

    public void setPost_modified(String post_modified) {
        this.post_modified = post_modified;
    }

    public String getPost_modified_gmt() {
        return post_modified_gmt;
    }

    public void setPost_modified_gmt(String post_modified_gmt) {
        this.post_modified_gmt = post_modified_gmt;
    }

    public String getPost_content_filtered() {
        return post_content_filtered;
    }

    public void setPost_content_filtered(String post_content_filtered) {
        this.post_content_filtered = post_content_filtered;
    }

    public String getPost_parent() {
        return post_parent;
    }

    public void setPost_parent(String post_parent) {
        this.post_parent = post_parent;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getMenu_order() {
        return menu_order;
    }

    public void setMenu_order(String menu_order) {
        this.menu_order = menu_order;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getPost_mime_type() {
        return post_mime_type;
    }

    public void setPost_mime_type(String post_mime_type) {
        this.post_mime_type = post_mime_type;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
