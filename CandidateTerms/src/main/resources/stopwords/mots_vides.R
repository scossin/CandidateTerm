library(tm)
mots_vides <- tm::stopwords(kind="fr")
mots_vides <- sort(mots_vides)
writeLines(mots_vides,"../stopwords/mots_vides_tm.txt")