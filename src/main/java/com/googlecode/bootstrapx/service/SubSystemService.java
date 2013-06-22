package com.googlecode.bootstrapx.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.googlecode.bootstrapx.model.Feature;
import com.googlecode.bootstrapx.model.SubSystem;


@Service
public class SubSystemService {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private Map<Integer, SubSystem> subSystemMap = null;
	private List<SubSystem> subSystemList = new ArrayList<SubSystem>();

	private Comparator<Feature> featureComparator = new Comparator<Feature>(){
		public int compare(Feature o1, Feature o2) {
			return o1.getSequence()-o2.getSequence();
		}
		
	};

	private Comparator<SubSystem> subSystemComparator = new Comparator<SubSystem>(){

		@Override
		public int compare(SubSystem o1, SubSystem o2) {
			return o1.getSequence() - o2.getSequence();
		}
		
	};

	public SubSystem getSubSystemById(int id){
		return getSubSystemMap().get(id);
	}
	public List<SubSystem> getSubSystemList(){
		if(subSystemMap == null){
			getSubSystemMap();
		}
		return subSystemList;
	}
	public Map<Integer, SubSystem> getSubSystemMap(){
		if(subSystemMap == null){
			subSystemMap = new HashMap<Integer, SubSystem>();
			
			InputStream is = null;
			try {
				is = SubSystemService.class.getResourceAsStream("/subsystem.xml");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = dbf.newDocumentBuilder();
	            Document doc = db.parse(is);
	            Element subsystems = doc.getDocumentElement();
	            
	            NodeList subSystemElementList = subsystems.getElementsByTagName("Subsystem");
	            for (int i = 0; i < subSystemElementList.getLength(); i++) {
	                Element subSystemElement = (Element) subSystemElementList.item(i);
	                int id = NumberUtils.toInt(subSystemElement.getAttribute("id"));
	                String name = subSystemElement.getAttribute("name");
	                String image = subSystemElement.getAttribute("image");
	                int sequence = NumberUtils.toInt(subSystemElement.getAttribute("sequence"));
	                
	                SubSystem subSystem = new SubSystem();
	                subSystem.setId(id);
	                subSystem.setName(name);
	                subSystem.setImage(image);
	                subSystem.setSequence(sequence);

	                List<Feature> featureList = new ArrayList<Feature>();
	                NodeList fElementList = subSystemElement.getElementsByTagName("Feature");
	                for(int j=0;j<fElementList.getLength();j++){
	                	Element fElement = (Element) fElementList.item(j);
		                String fname = fElement.getAttribute("name");
		                String flink = fElement.getAttribute("link");
		                int fsequence = NumberUtils.toInt(fElement.getAttribute("sequence"));
		                Feature feature = new Feature();
		                feature.setName(fname);
		                feature.setLink(flink);
		                feature.setSequence(fsequence);
		                
		                featureList.add(feature);
	                }
	                Collections.sort(featureList, featureComparator);
	                subSystem.setFeatureList(featureList);
	                
	                subSystemMap.put(subSystem.getId(), subSystem);
	                subSystemList.add(subSystem);
	            }
	        }catch (Exception e) {
	            LOGGER.error("初始化系统失败", e);
	        }
		}
		Collections.sort(subSystemList, subSystemComparator);
		return subSystemMap;
	}
}
