package resource;

import org.neo4j.graphdb.RelationshipType;

/**
 * Enum�ration des types de relations entre noeuds du graphe.
 * 
 * Ce type est � indiquer � la cr�ation d'une relation.
 * 
 * @author Guillaume
 */
public enum NodesRelationship implements RelationshipType {
	ROLE, SECURE, INSTANCE_OF, HAS_NATURE, HAS_FATHER
}