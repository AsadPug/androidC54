import React from 'react';
import {Text, Button, View, TextInput, StyleSheet} from 'react-native';
import {Slider} from '@react-native-assets/slider';


function EcranAjouter({navigation}){
    return(
        <View>
            <Text>Ajouter evaluation de bières de microbrasseries</Text>
            <TextInput placeholder="Entrez le nom de la bière"></TextInput>
            <View style={styles.rangee}>
                <Text style={{flex:3}}>Evaluation</Text>
                <Slider maximumValue={5} minimumValue={0} style={{flex:3}}/>
                <Button title="Accueil" onPress={()=>navigation.navigate('Accueil')}></Button>
            </View>
            
        </View>
    );
}

const styles = StyleSheet.create(
    {
        rangee:{
            flexDirection:"row"
        }   
    }

)

export default EcranAjouter;