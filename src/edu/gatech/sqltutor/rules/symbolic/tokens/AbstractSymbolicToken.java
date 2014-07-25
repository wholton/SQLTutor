package edu.gatech.sqltutor.rules.symbolic.tokens;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.gatech.sqltutor.rules.symbolic.PartOfSpeech;
import edu.gatech.sqltutor.rules.symbolic.SymbolicException;

public abstract class AbstractSymbolicToken implements ISymbolicToken {
	protected Set<String> provenance = new LinkedHashSet<String>();
	
	protected ISymbolicToken parent;
	protected PartOfSpeech partOfSpeech;
	
	protected AbstractSymbolicToken(ISymbolicToken toCopy) {
		if( toCopy == null ) throw new NullPointerException("toCopy is null");
		Class<?> thisClass = this.getClass(), thatClass = toCopy.getClass();
		if( thisClass != thatClass ) {
			throw new SymbolicException("Expected type " + thisClass.getName() 
				+ ", found type " + thatClass.getName());
		}
		this.partOfSpeech = toCopy.getPartOfSpeech();
	}
	
	protected AbstractSymbolicToken(PartOfSpeech pos) {
		setPartOfSpeech(pos);
	}
	
	@Override
	public PartOfSpeech getPartOfSpeech() {
		return partOfSpeech;
	}
	
	public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}
	
	@Override
	public List<ISymbolicToken> getChildren() {
		return Collections.emptyList();
	}
	
	@Override
	public void addChild(ISymbolicToken child) {
		throw new SymbolicException(this + " does not support children.");
	}
	
	@Override
	public boolean removeChild(ISymbolicToken child) {
		return false;
	}
	
	@Override
	public Set<String> getProvenance() {
		return provenance;
	}
	
	@Override
	public ISymbolicToken getParent() {
		return parent;
	}
	
	@Override
	public void setParent(ISymbolicToken parent) {
		this.parent = parent;	
	}
	
	protected StringBuilder addTypeAndTag(StringBuilder b) {
		b.append(getType()).append('/');
		PartOfSpeech pos = getPartOfSpeech();
		if( pos != null )
			b.append(pos.getTag());
		else
			b.append("NULL");
		return b;
	}
	
	protected StringBuilder addPropertiesString(StringBuilder b) {
		return b;
	}
	
	protected String typeAndTag() {
		return addTypeAndTag(new StringBuilder()).toString();
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder("{");
		addPropertiesString(addTypeAndTag(b)).append('}');
		return b.toString();
	}
}
