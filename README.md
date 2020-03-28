# CandidateTerm

Extraction de groupes nominaux. Ce programme utilise [TreeTagger](https://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/) pour l'étiquetage morpho-syntaxique de chaque token (POS) puis une expression régulière pour extraire les groupes nominaux. 

## Installation

### Télécharger TreeTagger
A cette adresse : https://www.cis.uni-muenchen.de/~schmid/tools/TreeTagger/, accepter les termes de la licence et télécharger la version pour Linux (PC-Linux). Suivre les étapes pour l'installation en langue française et vérifier l'installation : 

```{bash}
echo 'Le vif renard brun saute par-dessus le chien paresseux' | cmd/tree-tagger-french 
```

```
reading parameters ...
	tagging ...
Le	DET:ART	le
vif	ADJ	vif
renard	NOM	renard
brun	ADJ	brun
saute	VER:pres	sauter
par-dessus	ADV	par-dessus
le	DET:ART	le
chien	NOM	chien
paresseux	ADJ	paresseux
	 finished.
```

**Les fichiers de TreeTagger doivent être placés à la racine du dossier TreeTagger, sans dossier intermédiaire:**

```
project
│   README.md
└───TreeTagger
   └───cmd
       │tree-tagger-french
```

### Compiler avec [Maven](https://maven.apache.org/download.cgi)
```
export TREETAGGER_HOME=$PWD/TreeTagger
mvn clean package
```
Si TreeTagger a correctement été installé, les tests unitaires réussisent. 

### Lancer le tomcat avec Docker
Voir le fichier .env pour configurer le nom du containeur et son port. 
```
docker-compose up -d
```

Vérifier l'extraction des groupes nominaux : 
```
curl -d 'Le vif renard brun saute par-dessus le chien paresseux.' -H "Content-Type: application/x-www-form-urlencoded" -X POST http://localhost:8893/CTapi-0.0.1-SNAPSHOT/CTstringInputBody
```

### Sortie
Le programme retourne un objet JSON contenant un array de termes candidats (CT), chaque terme candidat est décrit par : 

* candidateTermString: la chaîne de caractère comme elle figure dans le texte
* startPosition: la position du début du candidateTermString dans la phrase
* absoluteStartPosition: la position du début du candidateTermString dans le texte
* endPosition: la position de la fin du candidateTermString dans la phrase
* absoluteStartPosition: la position de la fin du candidateTermString dans le texte
* candidateTerm: le candidateTermString normalisé en retirant les espaces
* normalizedTerm: le candidateTerm normalisé en retirant les accents et en mettant en miniscule
* lemmaTerm: le normalizedTerm lemmatisé par TreeTagger
* stemTerm: le stem du normalizedTerm

Un array de phrases est aussi retourné. Chaque phrase est décrite par : 

* phraseStart: le début de la phrase dans le texte
* phraseEnd: la fin de la phrase dans le texte
* content: le texte de la phrase
* phraseNumber: le numéro de la phrase

La séparation du texte en phrases utilise [Apache OpenNLP pour le français](http://enicolashernandez.blogspot.fr/2012/12/apache-opennlp-fr-models.html)

## Licence
Le programme est sous licence MIT cependant il utilise Apache OpenNLP et TreeTagger qui permettent une **utilisation gratuite à des fins de recherche uniquement**. 