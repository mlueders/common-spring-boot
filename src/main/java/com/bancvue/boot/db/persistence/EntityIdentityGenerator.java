/**
 * Copyright 2014 BancVue, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
