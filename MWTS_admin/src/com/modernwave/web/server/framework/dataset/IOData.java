package com.modernwave.web.server.framework.dataset;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class IOData extends LinkedHashMap<String, Object>{
	
	Logger log = Logger.getLogger(this.getClass());	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private Logger log = Logger.getLogger(IOData.class);
	
	public String getString(String key){
		Object o = super.get(key);
		return (o == null) ? "" : o.toString(); 
	}
	
	public int getInt(String key){
			Object o = super.get(key);
			if(o == null)
				throw new NullPointerException("key "+key+" value is null");
			if(o instanceof Integer){
				return ((Short)o).intValue();
			}else if(o instanceof Long){
				return ((Long)o).intValue();
			}else if(o instanceof Double){
				return ((Double)o).intValue();
			}else if(o instanceof BigDecimal){
				return ((BigDecimal)o).intValue();
			}else if(o instanceof Short){
				return ((Short)o).intValue();
			}else if(o instanceof Float){
				return ((Float)o).intValue();
			}
			return Integer.parseInt(o.toString());
	}
	
	public long getLong(String key){
		Object o = super.get(key);
		//log.debug("getLong class type["+o.getClass());
		if(o instanceof Integer){
			return ((Short)o).longValue();
		}else if(o instanceof Long){
			return ((Long)o).longValue();
		}else if(o instanceof Double){
			return ((Double)o).longValue();
		}else if(o instanceof BigDecimal){
			return ((BigDecimal)o).longValue();
		}else if(o instanceof Short){
			return ((Short)o).longValue();
		}else if(o instanceof Float){
			return ((Float)o).longValue();
		}
		return Long.parseLong(o.toString());
	}
	

	public double getDouble(String key){
		Object o = super.get(key);
		//log.debug("getDouble class type["+o.getClass());
		if(o instanceof Integer){
			return ((Short)o).doubleValue();
		}else if(o instanceof Long){
			return ((Long)o).doubleValue();
		}else if(o instanceof Double){
			return ((Double)o).doubleValue();
		}else if(o instanceof BigDecimal){
			return ((BigDecimal)o).doubleValue();
		}else if(o instanceof Short){
			return ((Short)o).doubleValue();
		}else if(o instanceof Float){
			return ((Float)o).doubleValue();
		}
		return Double.parseDouble(o.toString());
	}
	
	public BigDecimal getBigDecimal(String key){
		Object o = super.get(key);
		return (o instanceof BigDecimal)?(BigDecimal)o:new BigDecimal(o.toString());
	}
	
	public short getShort(String key){
		Object o = super.get(key);
		//log.debug("getShort class type["+o.getClass());
		if(o instanceof Integer){
			return ((Short)o).shortValue();
		}else if(o instanceof Long){
			return ((Long)o).shortValue();
		}else if(o instanceof Double){
			return ((Double)o).shortValue();
		}else if(o instanceof BigDecimal){
			return ((BigDecimal)o).shortValue();
		}else if(o instanceof Short){
			return ((Short)o).shortValue();
		}else if(o instanceof Float){
			return ((Float)o).shortValue();
		}
		return Short.parseShort(o.toString());
	}
	
	public float getFloat(String key){
		Object o = super.get(key);
		//log.debug("getFloat class type["+o.getClass());
		if(o instanceof Integer){
			return ((Short)o).floatValue();
		}else if(o instanceof Long){
			return ((Long)o).floatValue();
		}else if(o instanceof Double){
			return ((Double)o).floatValue();
		}else if(o instanceof BigDecimal){
			return ((BigDecimal)o).floatValue();
		}else if(o instanceof Short){
			return ((Short)o).floatValue();
		}else if(o instanceof Float){
			return ((Float)o).floatValue();
		}
		return Float.parseFloat(o.toString());
	}
	
	public boolean getBoolean(String key){
		Object o = super.get(key); 
	//	log.debug("getBoolean class type["+o.getClass());
		return ((Boolean)o).booleanValue();
	}
	
	public List<String> getKeyList(){
		Iterator<Entry<String, Object>> iter = this.entrySet().iterator();
		ArrayList<String> columnKeyList = new ArrayList<String>();
		while (iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			String key = entry.getKey();
			columnKeyList.add(key);
		}
		return columnKeyList;
	}
	
	
	
	public void printMap(){
		Set<String> keyset =  this.keySet();
		Iterator iter = keyset.iterator();
		while(iter.hasNext()){
			String key = ""+iter.next();
			log.debug("key["+key+"]value["+this.get(key)+"]");
		}
	}
}
