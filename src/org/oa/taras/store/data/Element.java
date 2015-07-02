package org.oa.taras.store.data;

import com.google.gson.annotations.SerializedName;

public class Element {

    @SerializedName("id")
    private final long id;
    @SerializedName("name")
    private String name;
    @SerializedName("year")
    private String year;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("elementDescription")
    private String elementDescription;
    
    public Element(long id, String name, String year, int quantity,
			String elementDescription) {
		this.id = id;
		this.name = name;
		this.year = year;
		this.quantity = quantity;
		this.elementDescription = elementDescription;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getElementDescription() {
		return elementDescription;
	}

	public void setElementDescription(String elementDescription) {
		this.elementDescription = elementDescription;
	}

	public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Element album = (Element) o;

        return id == album.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
