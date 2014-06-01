package com.bancvue.boot.db.persistence;

import com.bancvue.boot.db.domain.JpaEntity;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentityGenerator;

public class EntityIdentityGenerator extends IdentityGenerator {

	@Override
	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		if (obj == null) throw new HibernateException(new NullPointerException());
		assertObjectImplementsEntity(obj);

		Long objId = ((JpaEntity) obj).getId();
		if (objId == null || objId == 0) {
			Serializable id = super.generate(session, obj);
			return id;
		} else {
			return ((JpaEntity) obj).getId();
		}
	}

	private void assertObjectImplementsEntity(Object obj) {
		if ((obj instanceof JpaEntity) == false) {
			throw new RuntimeException("Object must implement " + JpaEntity.class);
		}
	}

}
