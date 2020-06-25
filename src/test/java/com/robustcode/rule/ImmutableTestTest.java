package com.robustcode.rule;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.robustcode.test.ImmutableTest;

public class ImmutableTestTest {

	@Test
	public void test() {
		PojoClass pc = PojoClassFactory.getPojoClass(SamplePojo.class);
		ImmutableTest it = new ImmutableTest();
		it.run(pc);
	}

}
