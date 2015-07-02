package org.oa.taras.store.data;

import com.google.gson.annotations.SerializedName;

public class Model {

	@SerializedName("id")
	private final long id;	
	@SerializedName("name")
	private String name;
	@SerializedName("code")
	private String code;
	@SerializedName("description")
	private String description;
	@SerializedName("model_set_name")
	private String model_set_name ;
	public Model(long id, String name, String code, String description) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.description = description;
		this.model_set_name = name+"_model_set";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getModel_set_name() {
		return model_set_name;
	}
	public void setModel_set_name(String model_set_name) {
		this.model_set_name = model_set_name;
	}
	public long getId() {
		return id;
	}
}
