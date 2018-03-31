package sk.gursky.simplestudent.nosql;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import de.undercouch.bson4jackson.BsonFactory;
import sk.gursky.simplestudent.nosql.aislike.DaoFactory;
import sk.gursky.simplestudent.nosql.aislike.entity.SimpleStudent;
import sk.gursky.simplestudent.nosql.aislike.entity.Student;
import sk.gursky.simplestudent.nosql.aislike.entity.StudijnyProgram;
import sk.gursky.simplestudent.nosql.aislike.entity.Studium;

public class Convertors {

	public static void main(String[] args) throws Throwable {
		List<Student> students = DaoFactory.INSTANCE.getStudentDao().getAll();
		String json = getJSONFromObject(students);
		System.out.println("Json size " + json.length());
		byte[] bson = getBSONFromObject(students);
		System.out.println("Bson size " + bson.length);
		String xml = getXMLFromObject(students);
		System.out.println("XML size " + xml.length());
		List<StudijnyProgram> studijneProgramy = new ArrayList<StudijnyProgram>();
		for (Student s : students) {
			for (Studium studium: s.getStudium()) {
				studijneProgramy.add(studium.getStudijnyProgram());
			}
		}
		String csv = getCSVFromStudijnyProgram(studijneProgramy);
		System.out.println("CSV size " + csv.length());
		List<SimpleStudent> simpleStudents = DaoFactory.INSTANCE.getStudentDao().getSimpleStudents();
		String csv2 = getCSVFromSimpleStudents(simpleStudents);
		System.out.println("CSV size " + csv2.length());
		String yaml = getYAMLFromObject(students);
		System.out.println("YAML size " + yaml.length());
//		System.out.println(json);
//		System.out.println(new String(bson));
//		System.out.println(xml);
//		System.out.println(csv);
		System.out.println(csv2);
//		System.out.println(yaml);
	}

	public static String getJSONFromObject(Object o) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		return mapper.writeValueAsString(o);	
	}
	
	public static byte[] getBSONFromObject(Object o) throws IOException {
		BsonFactory bsonFactory = new BsonFactory();
		ObjectMapper mapper = new ObjectMapper(bsonFactory);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		mapper.writeValue(baos, o);
		return baos.toByteArray();
	}
	
	public static String getXMLFromObject(Object o) throws JsonProcessingException {
		XmlMapper xmlMapper = new XmlMapper();
//		xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		return xmlMapper.writeValueAsString(o);
	}

	public static String getCSVFromStudijnyProgram(List<StudijnyProgram> studijneProgramy) throws IOException {
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema schema = csvMapper.schemaFor(StudijnyProgram.class).withHeader();
		ObjectWriter writer = csvMapper.writer(schema);
		return writer.writeValueAsString(studijneProgramy);
	}

	public static String getCSVFromSimpleStudents(List<SimpleStudent> simpleStudents) throws IOException {
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema schema = csvMapper.schemaFor(SimpleStudent.class).withHeader();
		ObjectWriter writer = csvMapper.writer(schema);
		return writer.writeValueAsString(simpleStudents);
	}

	public static String getYAMLFromObject(Object o) throws JsonProcessingException {
		YAMLMapper yamlMapper = new YAMLMapper();
//		yamlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		return yamlMapper.writeValueAsString(o);
	}
}