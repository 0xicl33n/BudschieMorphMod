package de.budschie.bmorph.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class ProtectedGetterAccess<C, T>
{
	private LazyOptional<Method> method;
	
	/** A way of accessing protected fields.
	 * @param classContainingField This is the class the field is a member of.
	 * @param fieldName This is the SRG name (unmapped name) of the field. **/
	public ProtectedGetterAccess(Class<C> classContainingField, String fieldName)
	{
		this.method = LazyOptional.of(() ->
		{
			Method innerField = ObfuscationReflectionHelper.findMethod(classContainingField, fieldName);
			innerField.setAccessible(true);
			return innerField;
		});
	}
	
	/** Returns the value of the field, or null if there was an error. **/
	@SuppressWarnings("unchecked")
	public T getValue(C instance)
	{
		try
		{
			return (T)method.resolve().get().invoke(instance);
		} 
		catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
