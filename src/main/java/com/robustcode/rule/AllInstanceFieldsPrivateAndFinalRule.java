package com.robustcode.rule;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoField;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;

public class AllInstanceFieldsPrivateAndFinalRule implements Rule {

	@Override
	public void evaluate(PojoClass pojoClass) {
		List<PojoField> nonPrivateFinalFields = pojoClass.getPojoFields().stream().filter(pf-> !pf.isStatic() && !(pf.isFinal() && pf.isPrivate())).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(nonPrivateFinalFields))
			Affirm.fail(String.format("Non 'private final' instance variables=[%s] not allowed", nonPrivateFinalFields));

	}

}
