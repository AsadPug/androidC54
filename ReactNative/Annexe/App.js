/**
 * Annexe 4 - cours C54
 * App de départ à modifier par les étudiants
 * 
 */

import React,{ useState } from 'react';

import {

  Text,
  Button,
  Image,
  View,
  StyleSheet,
  ScrollView
} from 'react-native';

import { liste } from './donnees.js';

function Galerie() {
  const [index, setIndex] = useState(0);
  const [affichage, setAffichage] = useState(false);
  let sculpture = liste[index];
  const remoteImage = { uri: sculpture.url };

  let visible = affichage ? styles.afficheDesc : styles.cacheDesc;

  function handleClick() {
    if (index < 3){
      setIndex(index+1);
    }
    else{
      setIndex(0);
    }
      
  } 

  function precedent(){
    if (index > 0)
      setIndex(index-1);
    else
      setIndex(3);
  }

  function affiche(){

  }


  return (
    <ScrollView contentContainerStyle= {styles.scroll}>
    <View style={styles.main}>
      <View style={styles.buttons}>
        <Button onPress={precedent} title="Précédent"/>
        <Button onPress={handleClick} title="Suivant"/>
      </View>
   
      
      <Text>
        {sculpture.name + "de " +sculpture.artist}
      </Text>
      <Text>  
        ({index + 1} of {liste.length})
      </Text>

      <Image style={styles.image} source={remoteImage} />
     
    </View>
    </ScrollView>
  );
}


const styles = StyleSheet.create(
  {
    image : {
      width:160,
      height:160,
    },

    main : {
      alignItems:'center',
      justifyContent:'center',
      flex:1
    },

    buttons :{
      flexDirection:'row',
      justifyContent:'space-between',
      width:'50%',
    },

    afficheDesc: {

    },
    cacheDesc: {

    },
    scroll: {
      flexGrow:1,
      
    }
  }
)
export default Galerie;
