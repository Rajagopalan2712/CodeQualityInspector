package com.robustcode.test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.construct.InstanceFactory;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.test.Tester;

public class AllArgsConstructorTester implements Tester {

	@Override
	public void run(PojoClass pojoClass) {
		List<PojoField> nonPrivateFinalFields = pojoClass.getPojoFields().stream().filter(pf-> !pf.isStatic() && !(pf.isFinal() && pf.isPrivate())).collect(Collectors.toList());
		List<Class<?>> fieldTypes = pojoClass.getPojoFields().stream().filter(pf->!pf.isStatic()).map(pf->pf.getType()).collect(Collectors.toList());
		Optional<PojoMethod> allArgsConstructor = pojoClass.getPojoConstructors().stream().filter(pc->Arrays.asList(pc.getParameterTypes()).containsAll(fieldTypes)).findFirst();
		if(allArgsConstructor.isEmpty())
			Affirm.fail(String.format("All Argument Constructor not present", nonPrivateFinalFields));
		
		List<Object> constructorArguments = allArgsConstructor.get().getPojoParameters().stream().map(RandomFactory::getRandomValue).collect(Collectors.toList());
		Object instance = InstanceFactory.getInstance(pojoClass, constructorArguments.toArray());
		
		
		List<Object> instanceState = pojoClass.getPojoFields().stream().map(pf->pf.get(instance)).collect(Collectors.toList());
		Affirm.affirmTrue("All Arguments constructor not initializing the variables properly", instanceState.containsAll(constructorArguments));

	}

}
