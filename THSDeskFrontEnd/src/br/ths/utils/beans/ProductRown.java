package br.ths.utils.beans;

import java.text.DecimalFormat;

import br.ths.beans.Product;

public class ProductRown extends Product{
	
	private String priceFormat;
	private Product product;
	

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getPriceFormat() {
		DecimalFormat df = new DecimalFormat("###,###,###.##");
		this.priceFormat = df.format(getPrice());
		return this.priceFormat;
	}

	public void setPriceFormat(String priceFormat) {
		this.priceFormat = priceFormat;
	}
	
}
