import React from 'react';
import {Text, Button, View} from 'react-native';

function EcranAccueil({navigation}){
    return(
        <View>
            <Text>Evaluation de bières de microbrasseries</Text>
            <Button title="ajouter une évaluation" onPress={()=>navigation.navigate('AjouterEvaluation')}></Button>
            <Button title="Voir les évaluations"></Button>
        </View>
    );
}

export default EcranAccueil;