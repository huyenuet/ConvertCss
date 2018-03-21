package src.main.java.parser;

import com.steadystate.css.parser.selectors.*;

import java.util.ArrayList;

/**
 * Created by smart on 11/12/2017.
 */
public class SacSelector {

    public String SacConditionalSelector(ConditionalSelectorImpl conditionalSelector) {
//        int selectorType = conditionalSelector.getSimpleSelector().getSelectorType();
//        switch (selectorType) {
//            case 0:
//                SacConditionalSelector((ConditionalSelectorImpl) conditionalSelector.getSimpleSelector());
//                break;
//            case 4:
//                selectorList.add(SacElementNodeSelector((ElementSelectorImpl) conditionalSelector.getSimpleSelector()));
//                break;
//            case 9:
//                selectorList.add(SacClassCondition((ClassConditionImpl) conditionalSelector.getSimpleSelector()));
//                break;
//            case 11:
//                SacChildSelector((ChildSelectorImpl) conditionalSelector.getSimpleSelector());
//                break;
//        }
//        int conditionType = conditionalSelector.getCondition().getConditionType();
//        switch (conditionType) {
//            case 10:
//                selectorList.add(SacPseudoCassCondition ((PseudoClassConditionImpl) conditionalSelector.getCondition()));
//                break;
//            case 9:
//                selectorList.add(SacClassCondition((ClassConditionImpl) conditionalSelector.getCondition()));
//                break;
//        }

        return conditionalSelector.toString();
    }
    public ArrayList<String> SacChildSelector (ArrayList<String> selectorList, ChildSelectorImpl childSelector) {
        // ancestorSelector
        int selectorType = childSelector.getAncestorSelector().getSelectorType();
        switch (selectorType) {
            // child is an ancestorSelector
            case 11:
                SacChildSelector(selectorList,(ChildSelectorImpl) childSelector.getAncestorSelector());
                break;
            //child is an elementSelector
            case 4:
                selectorList.add(SacElementNodeSelector((ElementSelectorImpl) childSelector.getAncestorSelector()));
                break;
            // child is a conditionalSelector
            case 0:
                selectorList.add(SacConditionalSelector((ConditionalSelectorImpl) childSelector.getAncestorSelector()));
                break;
            case 9:
                selectorList.add(SacClassCondition((ClassConditionImpl) childSelector.getSimpleSelector()));
                break;
        }
        // simpleSelector
        selectorType = childSelector.getSimpleSelector().getSelectorType();
        switch (selectorType) {
            case 0:
                selectorList.add(SacConditionalSelector((ConditionalSelectorImpl) childSelector.getSimpleSelector()));
                break;
            case 4:
                selectorList.add(SacElementNodeSelector((ElementSelectorImpl) childSelector.getSimpleSelector()));
                break;
            case 11:
                SacChildSelector(selectorList,(ChildSelectorImpl) childSelector.getSimpleSelector());
                break;
            case 9:
                selectorList.add(SacClassCondition((ClassConditionImpl) childSelector.getSimpleSelector()));
                break;
        }
        return selectorList;
    }
    public String SacPseudoCassCondition(PseudoClassConditionImpl pseudoClassCondition) {
        return pseudoClassCondition.getValue();
    }
    public String SacElementNodeSelector (ElementSelectorImpl elementSelector) {
        return elementSelector.getLocalName();
    }
    public String SacClassCondition (ClassConditionImpl classCondition) {
        // ConditionType = 9
        return classCondition.getValue();
    }
}
