package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.Organ;
import com.digdes.rst.orgstructure.persistance.model.Person;

/**
 * @author Samoylov Ilya
 *         Date: 25.01.18.
 *         Copyright http://digdes.com
 */

public interface SearchService {
    String index(Person person, String appId, String userId);

    String index(Organ organ, String appId, String userId);

    boolean unindexOrgan(String appId, String userID, Long id);

    boolean unindexPerson(String appId, String userID, Long id);
}
