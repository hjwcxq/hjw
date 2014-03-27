package com.tv189.dzlc.adapter.po.sqlpo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "my_collect")
public class StockVideo {
	@DatabaseField(id = true)
	private String id;
	@DatabaseField
	private String beginTime;
	@DatabaseField
	private String cast;
	@DatabaseField
	private String cd;
	@DatabaseField
	private String decade;
	@DatabaseField
	private String detail;
	@DatabaseField
	private String director;
	@DatabaseField
	private String img;
	@DatabaseField
	private String length;
	@DatabaseField
	private String mbcontentid;
	@DatabaseField
	private String nation;
	@DatabaseField
	private String nowcount;
	@DatabaseField
	private String ot;
	@DatabaseField
	private String path;
	@DatabaseField
	private String plat;
	@DatabaseField
	private String productID;
	@DatabaseField
	private String sumcount;
	@DatabaseField
	private String tag;
	@DatabaseField
	private String title;

	// ==================== 后面添加的
	@DatabaseField
	public String uid;


	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getDecade() {
		return decade;
	}

	public void setDecade(String decade) {
		this.decade = decade;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getMbcontentid() {
		return mbcontentid;
	}

	public void setMbcontentid(String mbcontentid) {
		this.mbcontentid = mbcontentid;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNowcount() {
		return nowcount;
	}

	public void setNowcount(String nowcount) {
		this.nowcount = nowcount;
	}

	public String getOt() {
		return ot;
	}

	public void setOt(String ot) {
		this.ot = ot;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPlat() {
		return plat;
	}

	public void setPlat(String plat) {
		this.plat = plat;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getSumcount() {
		return sumcount;
	}

	public void setSumcount(String sumcount) {
		this.sumcount = sumcount;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String type;

	public boolean isCanPlay() {
		return this.productID != null && this.productID.endsWith("100");
	}
}
