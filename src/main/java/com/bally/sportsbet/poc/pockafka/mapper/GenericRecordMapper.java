/**
 * 
 */
package com.bally.sportsbet.poc.pockafka.mapper;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.reflect.ReflectData;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.Assert;

/**
 * @author Orlei Bicheski
 *
 */
public class GenericRecordMapper {
	
	public <T> T toObject(GenericData.Record record, Class<T> clazz) {
		Assert.notNull(record, "record must not be null");
		
		Schema schema = ReflectData.get().getSchema(clazz);
		Assert.isTrue(schema.getFields().equals(record.getSchema().getFields()), "Schema fields didn't match");

		try {
			T object = clazz.getDeclaredConstructor().newInstance();

			record.getSchema().getFields().stream()
				.filter(Objects::nonNull)
				.map(property -> property.name())
				.forEach(name -> PropertyAccessorFactory.forDirectFieldAccess(object)
						.setPropertyValue(name, record.get(name)));

			return object;
		} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
