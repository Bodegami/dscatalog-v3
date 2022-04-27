package com.devsuperior.dscatalog.repositories;

public interface ProductRepositoryRedis {

	//String
		void setProgrammerAsString(String idKey, String programmer);
		
		String getProgrammerAsString(String idKey);
	
}
