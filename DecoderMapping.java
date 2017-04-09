import org.w3c.dom.*;

import javax.xml.parsers.*;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DecoderMapping 
{
	static HashMap<String, String> globals=new HashMap();
	
	public static void main(String[] args) throws Exception 
	{
		Scanner in=new Scanner(System.in);
		//System.out.println("Enter the Decoder Name to get the Mapping:");
		//String decoder=in.nextLine();
		//D:\\MTN-Iran_CCN_CS5_ASN1.xml
		//Mapping output file initialization 
		//File exlFile = new File("D:\\mtns_msc_lte.xls");
		System.out.println("Enter the Output Excel Filename:");
		String outfile=in.next();
		String complte_out_file="D:\\"+outfile;
		File exlFile = new File(complte_out_file);
		
	    WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile);
	    int sheetNo=0;
		
		//File inputFile = new File("D:\\mtns_msc_lte.xml");
	    System.out.println("Enter the Output Decoder Name:");
	    String decoder_file=in.next();
		String complte_decoder_file="D:\\"+decoder_file;
	    File inputFile = new File(complte_decoder_file);
	    
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(inputFile);
		doc.getDocumentElement().normalize();
		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList mainMappings = doc.getElementsByTagName("Mappings");
		//First Mapping Node
		Node mainMappingsNode = mainMappings.item(0);            
        //System.out.println("\nCurrent Element :" + mainMappingsNode.getNodeName());  
	  
	    //Going to Mapping Node
        if (mainMappingsNode.getNodeType() == Node.ELEMENT_NODE) 
        {
        	 //Element mainMappingsElement = (Element) mainMappingsNode; 
        	 NodeList  mainMappingsChildList=mainMappingsNode.getChildNodes();
        	 
        	 
        	 
        	 /* gLOBAL vARIABLES */
        	 for ( int i=0; i<mainMappingsChildList.getLength();i++)
        	 {
        		 Node mainMappingsChildNode = mainMappingsChildList.item(i);
        		 if(mainMappingsChildNode.getNodeName().equals("Globals"))
        		 {
        			 if (mainMappingsChildNode.getNodeType() == Node.ELEMENT_NODE)
        			 {
        				 
        	        	 NodeList  globalsChildList = mainMappingsChildNode.getChildNodes();    	
       	        	 
        	        	 for(int j=0;j<globalsChildList.getLength();j++)
        	        	 {
        	        		 Node globalsChildNode=globalsChildList.item(j);
        	        		 if (globalsChildNode.getNodeType() == Node.ELEMENT_NODE)
                			 {
        	        			 Element globalsChildElement = (Element) globalsChildNode;
        	        			 //System.out.println(globalsChildElement.getAttribute("name")+" = "+globalsChildElement.getAttribute("initialValue"));
        	        			 globals.put(globalsChildElement.getAttribute("name"),globalsChildElement.getAttribute("initialValue"));
                			 }
        	        	 }
        	        /*	 Set set = globals.entrySet();
        	        	 Iterator globalloop = set.iterator();
        	        	 while(globalloop.hasNext())
        	        	 {
        	        		 Map.Entry me = (Map.Entry)globalloop.next();
        	                 System.out.print(me.getKey() + ": ");
        	                 System.out.println(me.getValue());
        	        	 } */
        			 }
        		 }	 
        	 }
        	 
        	 /* Assigning Global Variables */
        	 for ( int i=0; i<mainMappingsChildList.getLength();i++)
        	 {
        		 Node mainMappingsChildNode = mainMappingsChildList.item(i);
        		 if(mainMappingsChildNode.getNodeName().equals("Nodes"))
        		 {
        			 if (mainMappingsChildNode.getNodeType() == Node.ELEMENT_NODE)
        			 {
        				 NodeList  nodesChildList = mainMappingsChildNode.getChildNodes();
        	        	 for(int j=0;j<nodesChildList.getLength();j++)
        	        	 {
        	        		 Node nodesChildNode=nodesChildList.item(j);
        	        		 if (nodesChildNode.getNodeType() == Node.ELEMENT_NODE)
                			 {
        	        			 Element nodesChildElement = (Element) nodesChildNode;
        	        			 NodeList  nodeChildList = nodesChildNode.getChildNodes();        	        			 
            	        		 //System.out.println(nodesChildElement.getAttribute("type"));
        	        			 if(nodesChildElement.getAttribute("type").equals("Mapping")||nodesChildElement.getAttribute("type").equals("Filter"))
        	        			 {
        	        				 for(int k=0;k<nodeChildList.getLength();k++)
        	        				 {
        	        					 Node inputs= nodeChildList.item(k);
        	        					 if(inputs.getNodeName().equals("Inputs"))
        	        	        		 {
        	        						 if (inputs.getNodeType() == Node.ELEMENT_NODE)
        	        	        			 {
        	        	        				 NodeList  inputsList = inputs.getChildNodes();
        	        	        				 for(int l=0;l<inputsList.getLength();l++)
        	        	        				 {
        	        	        					 Node input=inputsList.item(l);
        	        	        					 if(input.getNodeName().equals("Input"))
        	        	        					 {
        	        	        						 if (input.getNodeType() == Node.ELEMENT_NODE)
            	        	                			 {
            	        	        						 NodeList  InputList = input.getChildNodes();
            	        	        						 for(int m=0;m<InputList.getLength();m++)
            	        	        						 {
            	        	        							 Node Events=InputList.item(m);
            	        	        							 if(Events.getNodeName().equals("Events"))
            	        	        							 {
            	        	        								 if(Events.getNodeType() == Node.ELEMENT_NODE)
            	        	        								 {
            	        	        									 NodeList EventsList=Events.getChildNodes();
            	        	        									 for(int n=0;n<EventsList.getLength();n++)
            	        	        									 {
            	        	        										 Node event=EventsList.item(n);
                    	        	        								 if(event.getNodeType() == Node.ELEMENT_NODE)
                    	        	        								 {
                    	        	        									 Element eventElement = (Element) event;
                    	        	        									 System.out.println(eventElement.getAttribute("target").substring(1));
                    	        	        									 
                    	        	        									 String global_var=eventElement.getAttribute("target").substring(1);
                    	        	        									 globals.put(global_var,eventElement.getAttribute("expression"));
                    	        	        									 
                    	        	        								 }
            	        	        									 }
            	        	        								 }
            	        	        							 }
            	        	        						 }
            	        	                			 } 
        	        	        					 }
        	        	        					 
        	        	        				 }
        	        	        			 }	 
        	        	        		 }
        	        				 }
        	        			 }
        	        			 if(nodesChildElement.getAttribute("type").equals("Record")||nodesChildElement.getAttribute("type").equals("Filter"))
        	        			 {
        	        				 for(int k=0;k<nodeChildList.getLength();k++)
        	        				 {
        	        					 Node outputs= nodeChildList.item(k);
        	        					 if(outputs.getNodeName().equals("Outputs"))
        	        	        		 {
        	        						 if (outputs.getNodeType() == Node.ELEMENT_NODE)
        	        	        			 {
        	        	        				 NodeList  outputsList = outputs.getChildNodes();
        	        	        				 for(int l=0;l<outputsList.getLength();l++)
        	        	        				 {
        	        	        					 Node output=outputsList.item(l);
        	        	        					 if(output.getNodeName().equals("Output"))
        	        	        					 {
        	        	        						 if (output.getNodeType() == Node.ELEMENT_NODE)
            	        	                			 {
            	        	        						 NodeList  OutputList = output.getChildNodes();
            	        	        						 for(int m=0;m<OutputList.getLength();m++)
            	        	        						 {
            	        	        							 Node Events=OutputList.item(m);
            	        	        							 if(Events.getNodeName().equals("Events"))
            	        	        							 {
            	        	        								 if(Events.getNodeType() == Node.ELEMENT_NODE)
            	        	        								 {
            	        	        									 NodeList EventsList=Events.getChildNodes();
            	        	        									 for(int n=0;n<EventsList.getLength();n++)
            	        	        									 {
            	        	        										 Node event=EventsList.item(n);
                    	        	        								 if(event.getNodeType() == Node.ELEMENT_NODE)
                    	        	        								 {
                    	        	        									 Element eventElement = (Element) event;
                    	        	        									 if(!(eventElement.getAttribute("type").equals("execute")))
                    	        	        									 {
                    	        	        									 //System.out.println(eventElement.getAttribute("expression"));
                    	        	        										 String global_var=eventElement.getAttribute("target").substring(1);
                    	        	        										 globals.put(global_var,eventElement.getAttribute("expression"));
                    	        	        									 }
                    	        	        								 }
            	        	        									 }
            	        	        								 }
            	        	        							 }
            	        	        						 }
            	        	                			 } 
        	        	        					 }
        	        	        					 
        	        	        				 }
        	        	        			 }	 
        	        	        		 }
        	        				 }
        	        			 }
                			 }
        	        	 }   	        	 
        				 
        			 }
        		 }
        	 }	 
        	 
        	 
        	 //Printing Global Variables.
        	 Set set = globals.entrySet();
        	 Iterator globalloop = set.iterator();
        	 while(globalloop.hasNext())
        	 {
        		 Map.Entry me = (Map.Entry)globalloop.next();
                 System.out.print(me.getKey() + ": ");
                 System.out.println(me.getValue());
        	 }
        	 
        	 
        	 
        	 /*Record Types */
        	 //HashMap<String, String> record_type[]=new HashMap[recordTypesChildList.getLength()];
        	 int no_of_record_types = 0;
        	 HashMap<String, String> record_type_name_type[] = null;
        	 HashMap<String, String> record_type_name_nullable[]=null;
        	 HashMap<Integer, String> record_type_index_name[]=null;
        	 for ( int i=0; i<mainMappingsChildList.getLength();i++)
        	 {
        		 Node mainMappingsChildNode_r = mainMappingsChildList.item(i);
        		 //System.out.println("Nodes: "+ mainMappingsChildNode.getNodeName());
        		 if(mainMappingsChildNode_r.getNodeName().equals("RecordTypes"))
        		 {
        			 if (mainMappingsChildNode_r.getNodeType() == Node.ELEMENT_NODE)
        			 {
        				 NodeList  recordTypesChildList = mainMappingsChildNode_r.getChildNodes();
        				 no_of_record_types=recordTypesChildList.getLength();
        				 record_type_name_type=new HashMap[recordTypesChildList.getLength()];
        				 record_type_name_nullable=new HashMap[recordTypesChildList.getLength()];
        				 record_type_index_name=new HashMap[recordTypesChildList.getLength()];
        	        	 for(int j=0;j<recordTypesChildList.getLength();j++)
        	        	 {
        	        		 record_type_name_type[j]=new HashMap();
        	        		 record_type_name_nullable[j]=new HashMap();
        	        		 record_type_index_name[j]=new HashMap();
        	        		 Node recordTypesChildNode=recordTypesChildList.item(j);
        	        		 if (recordTypesChildNode.getNodeType() == Node.ELEMENT_NODE)
                			 {
        	        			 Element recordTypesChildElement = (Element) recordTypesChildNode;
            	        		 //System.out.println(nodesChildElement.getAttribute("type"));
        	        			 record_type_index_name[j].put(0,recordTypesChildElement.getAttribute("name"));
        	        			 record_type_name_type[j].put(recordTypesChildElement.getAttribute("name"), "true");
        	        			 record_type_name_nullable[j].put(recordTypesChildElement.getAttribute("name"), "true");
        	        			 NodeList  FieldList = recordTypesChildNode.getChildNodes();
        	        			 int index=1;
        	        			 for(int k=0;k<FieldList.getLength();k++)
                	        	 {
        	        				 Node FieldNode=FieldList.item(k);
        	        				 if (FieldNode.getNodeType() == Node.ELEMENT_NODE)
                        			 {
                	        			 Element FieldElement = (Element) FieldNode;
                	        			 record_type_name_type[j].put(FieldElement.getAttribute("name"), FieldElement.getAttribute("type"));
                	        			 record_type_name_nullable[j].put(FieldElement.getAttribute("name"), FieldElement.getAttribute("nullable"));
                	        			 record_type_index_name[j].put(index,FieldElement.getAttribute("name"));
                	        			 index++;
                	       			 }	 
                	        	 }
                			 }
        				 
        			     }
        	        	 
        	        	/* for(int it=0;it<recordTypesChildList.getLength();it++)
        	        	 {
        	        		 System.out.println(it);
        	        		 //Set set1 = record_type_name_type[it].entrySet();
        	        		 Set set1 = record_type_index_name[it].entrySet();
        	        		 Iterator nt = set1.iterator();
        	        		 //Iterator nn = set2.iterator();
            	        	 while(nt.hasNext())
            	        	 {
            	        		 
            	        		 Map.Entry me = (Map.Entry)nt.next();
            	        		 //while(nn.hasNext())
                	        	 //{
                	        	//	 Map.Entry me1 = (Map.Entry)nn.next();
                	        	//	 if(me.getKey().equals(me1.getKey()))
                	        			 System.out.println(me.getKey()+ " : "+me.getValue());
                	        	 //}
            
            	        	 }
        	        	 } */
        	        	   	 
        	        	 
        		      }
        		 }	 
        	 }
        	 
        	 
        	 
        	 /*Mappings */
        	 for ( int i=0; i<mainMappingsChildList.getLength();i++)
        	 {
        		 Node mainMappingsChildNode = mainMappingsChildList.item(i);
        		 //System.out.println("Nodes: "+ mainMappingsChildNode.getNodeName());
        		 if(mainMappingsChildNode.getNodeName().equals("Nodes"))
        		 {
        			 if (mainMappingsChildNode.getNodeType() == Node.ELEMENT_NODE)
        			 {
        				 //Element Element = (Element) mainMappingsChildNode; 
        	        	 NodeList  nodesChildList = mainMappingsChildNode.getChildNodes();
        	        	 for(int j=0;j<nodesChildList.getLength();j++)
        	        	 {
        	        		 Node nodesChildNode=nodesChildList.item(j);
        	        		 if (nodesChildNode.getNodeType() == Node.ELEMENT_NODE)
                			 {
        	        			 Element nodesChildElement = (Element) nodesChildNode;
            	        		 //System.out.println(nodesChildElement.getAttribute("type"));
        	        			 if(nodesChildElement.getAttribute("type").equals("Mapping"))
        	        			 {
        	        				 String sheetName=nodesChildElement.getAttribute("name");
        	        				 String rec_type=nodesChildElement.getAttribute("recordType");
        	        				 NodeList nodeChildList=nodesChildNode.getChildNodes();
        	        				 
        	        				 HashMap<String, String> mapping_global_variables=new HashMap();
        	        				 
        	        				 for(int k=0;k<nodeChildList.getLength();k++)
        	        				 {
        	        					 Node nodeChildNode=nodeChildList.item(k);
        	        					 
        	        					 //Mapping Input Global Variables
        	        					 if(nodeChildNode.getNodeName().equals("Inputs"))
        	        					 {
        	        						 if (nodeChildNode.getNodeType() == Node.ELEMENT_NODE)
        	        	        			 {
        	        	        				 NodeList  inputsList = nodeChildNode.getChildNodes();
        	        	        				 for(int l=0;l<inputsList.getLength();l++)
        	        	        				 {
        	        	        					 Node input=inputsList.item(l);
        	        	        					 if(input.getNodeName().equals("Input"))
        	        	        					 {
        	        	        						 if (input.getNodeType() == Node.ELEMENT_NODE)
            	        	                			 {
            	        	        						 NodeList  InputList = input.getChildNodes();
            	        	        						 for(int m=0;m<InputList.getLength();m++)
            	        	        						 {
            	        	        							 Node Events=InputList.item(m);
            	        	        							 if(Events.getNodeName().equals("Events"))
            	        	        							 {
            	        	        								 if(Events.getNodeType() == Node.ELEMENT_NODE)
            	        	        								 {
            	        	        									 NodeList EventsList=Events.getChildNodes();
            	        	        									 for(int n=0;n<EventsList.getLength();n++)
            	        	        									 {
            	        	        										 Node event=EventsList.item(n);
                    	        	        								 if(event.getNodeType() == Node.ELEMENT_NODE)
                    	        	        								 {
                    	        	        									 Element eventElement = (Element) event;
                    	        	        									 //String global_var=eventElement.getAttribute("target").substring(1);
                    	        	        									 mapping_global_variables.put(eventElement.getAttribute("target"),eventElement.getAttribute("expression"));
                    	        	        								 }
            	        	        									 }
            	        	        								 }
            	        	        							 }
            	        	        						 }
            	        	                			 } 
        	        	        					 }
        	        	        					 
        	        	        				 }
        	        	        			 }
        	        					 }
        	        					 
        	        					 //Mapping Input Global Variables Ends
        	        					 
        	        					 
        	        					 //Mapping Logic 
        	        					 if(nodeChildNode.getNodeName().equals("Mappings"))
        	        					 {
        	        						 //Writing Mappings to Excel 
        	        						 WritableSheet writableSheet = writableWorkbook.createSheet(sheetName,sheetNo);
        	        						 sheetNo++;
        	        						 NodeList mappingsChildList=nodeChildNode.getChildNodes();
        	        						 int rownum=1;
        	        						 System.out.println("-------------MAPPING---------------");
        	        						 Label mappingNameHeader = new Label(0, 0, "Mapping Name");
        	        						 Label mappingDataType = new Label(1, 0, "Data Type");
        	        						 Label mappingNullable = new Label(2, 0, "Is Nullable");
	                	        			 Label MappingValueHeader = new Label(3, 0, "Mapping Value");
	                	        			 writableSheet.addCell(mappingNameHeader);
	                	        			 writableSheet.addCell(mappingDataType);
	                	        			 writableSheet.addCell(mappingNullable);
	                	        			 writableSheet.addCell(MappingValueHeader);
        	        						 
	                	        			 
	                	        			 HashMap<String, String> mapping=new HashMap();
	                	        			 for(int l=0;l<mappingsChildList.getLength();l++)
        	        						 {
        	        							 Node mappingsChildNode=mappingsChildList.item(l);
        	        							 if (mappingsChildNode.getNodeType() == Node.ELEMENT_NODE)
        	                        			 {
        	                	        			 Element mappingsChildElement = (Element) mappingsChildNode;
        	                	        			 mapping.put(mappingsChildElement.getAttribute("target"), mappingsChildElement.getAttribute("expression"));
        	                	        			    	                	        			 
        	                	        			 /*Global variable values in Inputs mapping
        	                	        			  * to be replaced with Mapping Variables
        	                	        			  */
        	                	        			 /*
        	                	        			 String map_var=mappingsChildElement.getAttribute("expression");
        	                	        			 int contains=map_var.indexOf('$');
        	                	        			 int contains1=map_var.indexOf("$$");
        	                	        			 while(contains>-1 && contains!=contains1)
        	                	        			 {
        	                	        				 
        	                	        			 }
        	                	        			 
        	                	        			 */
        	                	        			 
        	                	        			 /*
        	                	        			  * Mapping Variables Ends
        	                	        			  */
        	                	        			 //System.out.println(mappingsChildElement.getAttribute("target")+"  = " + mappingsChildElement.getAttribute("expression"));
        	                	        			 //Label mappingName = new Label(0, rownum, mappingsChildElement.getAttribute("target"));
        	                	        			 //Label MappingValue = new Label(1, rownum, mappingsChildElement.getAttribute("expression"));
        	                	        			 //writableSheet.addCell(mappingName); 
        	                	        			 //writableSheet.addCell(MappingValue);
        	                	        			 //rownum++;
        	                        			 }	 
        	        						 }
	                	        			 
	                	        			 
	                	        			 
	                	        			 int fingerprint_length=0;
	                	        			 for(int u=0;u<no_of_record_types;u++)
	                	        			 {
	                	        				 String s=record_type_index_name[u].get(0);
	                	        				 if(s!=null && s.equals(rec_type))
	                	        				 {
	                	        					 fingerprint_length=record_type_index_name[u].size();
	                	        					 //System.out.println(rec_type+fingerprint_length);    					 
	                	        					 for(int y=1;y<fingerprint_length;y++)
	                	        					 {
	                	        						 Label name = new Label(0, rownum, record_type_index_name[u].get(y));
	        	                	        			 Label data_type = new Label(1, rownum, record_type_name_type[u].get(record_type_index_name[u].get(y)));
	        	                	        			 Label null_or_not= new Label(2, rownum, record_type_name_nullable[u].get(record_type_index_name[u].get(y)));
	        	                	        			 if(mapping.get(record_type_index_name[u].get(y))!=null)
	        	                	        			 {
	        	                	        				 Label map_logic= new Label(3, rownum, mapping.get(record_type_index_name[u].get(y)));
	        	                	        				 writableSheet.addCell(map_logic);
	        	                	        			 }
	        	                	        			 
	        	                	        			 writableSheet.addCell(name); 
	        	                	        			 writableSheet.addCell(data_type);
	        	                	        			 writableSheet.addCell(null_or_not);
	        	                	        			 
	        	                	        			 rownum++;
	                	        					 }
	                	        					 
	                	        					 
	                	        					 //Global Variables Writing
	                	        					 rownum++;
	                	        					 Label global_var = new Label(0, rownum, "Global Variables");
	                	        					 writableSheet.addCell(global_var);
	                	        					 rownum++;
	                	        					 Label global_var_head = new Label(0, rownum, "Global Variable");
	                	        					 writableSheet.addCell(global_var_head);
	                	        					 Label global_exp_head = new Label(1, rownum, "Expression value");
	                	        					 writableSheet.addCell(global_exp_head);
	                	        					 rownum++;
	                	        					 
	                	        					 Set s1 = mapping_global_variables.entrySet();
	                	        		        	 Iterator global_mapping_loop = s1.iterator();
	                	        		        	 while(global_mapping_loop.hasNext())
	                	        		        	 {
	                	        		        		 Map.Entry global_var1 = (Map.Entry)global_mapping_loop.next();
	                	        		        		 Label var = new Label(0, rownum, (String) global_var1.getKey());
	                	        		        		 Label exp = new Label(1, rownum, (String) global_var1.getValue());
	                	        		        		 writableSheet.addCell(var); 
	                	        		        		 writableSheet.addCell(exp); 
	                	        		                // System.out.print(global_var1.getKey() + ": ");
	                	        		                 //System.out.println(global_var1.getValue());
	                	        		                 rownum++;
	                	        		        	 }
	                	        					 //Global Variables Writing Ends
	                	        		        	 
	                	        					 break;
	                	        				 }
	                	        			 }	 
        	        						 
        	        					 }
        	        					 
        	        					 /*Mapping Logic Ends */
        	        					 
        	        					 
        	        				 }
        	        			 }
                			 }
        	        		 
        	        	 }
        	        	 
        			 }
        		 }
        	 }
        	 
        	//System.out.println("First Name : " + mainMappingsElement.getElementsByTagName("Nodes").item(0).getTextContent());
        }
        writableWorkbook.write(); 
        writableWorkbook.close();
        

	}
}