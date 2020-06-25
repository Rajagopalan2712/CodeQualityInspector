package com.robustcode.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.rule.impl.NoPublicFieldsExceptStaticFinalRule;
import com.openpojo.validation.test.Tester;
import com.robustcode.rule.AllInstanceFieldsPrivateAndFinalRule;

public class ImmutableTest implements Tester{
	
	private static final List<Class<?>> knownImmutables = Arrays.asList(String.class, Integer.class, Double.class, Float.class, Boolean.class);

	@Override
	public void run(PojoClass pojoClass) {
		
		List<PojoClass> subClassesToTest = subClassesToTest(pojoClass);
		for(PojoClass pc: subClassesToTest)
			run(pc);
		
		new NoPublicFieldsExceptStaticFinalRule().evaluate(pojoClass);
		
		new AllInstanceFieldsPrivateAndFinalRule().evaluate(pojoClass);
		
		new AllArgsConstructorTester().run(pojoClass);
	}
	
	private List<PojoClass> subClassesToTest(PojoClass pojoClass) {
		return pojoClass.getPojoFields().stream().filter(pf->!pf.isPrimitive()).map(pf->PojoClassFactory.getPojoClass(pf.getType())).peek(System.out::println)
		.filter(pc->!pc.isEnum()).filter(pc->!knownImmutables.contains(pc.getClazz()))
		.filter(pc->!(pc.extendz(Collections.class) || pc.extendz(Map.class)))
		.collect(Collectors.toList());
	}

	
	
}
 