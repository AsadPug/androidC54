#======================================================================================================
#
# APPLICATION : Node.js LTS 18.15.0
# VERSION : 18.15.0
# FABRICANT : Node.js Foundation
# TYPE DE FICHIER : PS1
# MODE INSTALLATION : SILENT
# LANGAGE : Anglais
#
# AUTEUR : Hervé NATIVEL
# DATE : 31 mars 2023
# MODIFIÉ PAR : Éric LABONTÉ ( OCT 23 )
# COMMENTAIRE: React Native est installé par les étudiants.
#
#======================================================================================================

# ETUDIANT : Copier le contenu du script .ps1 dans Windows PowerShell ISE.

#--------- PRÉPARATION -----------

 Param([Parameter(Mandatory=$True)]
        [String]$nomProjet
       )


	# Variable pour le suivi de l'installation
	$PackageName = 'Node.js LTS 18.15.0 - React Native'
	$InstallPath = 'C:\travail\Nodejs'
	$NpmPath = 'C:\travail\npm'
	$ReactNativePath = 'C:\travail\ReactNative'
	$StringInstallProcess =  "Install_$PackageName.ps1 ;`n"

#-------- INSTALLATION -----------

	# Commandes d'installation principales
	# Installer et configurer React Native
	$NpmPrefix = cmd /c "$InstallPath\npm" config get prefix
	$NpmCache = cmd /c "$InstallPath\npm" config get cache
	if (($NPMPrefix -eq $NpmPath) -and ($NPMCache -eq "$NpmPath-cache")) {
		$StringInstallProcess += "[OK]`t Prefix+Cache ; $NpmPrefix ; $NpmCache`n"
		
		# Mettre à jour NPM
		$NpmVersion = cmd /c "$InstallPath\npm" -v
		cmd /c "$InstallPath\npm" install --global npm@latest
		if ($?){
			cmd /c "$InstallPath\npm" install --global --prefix $NpmPath -cache "$NpmPath-cache" npm@latest
			$NewNpmVersion = cmd /c "$InstallPath\npm" -v
			$StringInstallProcess += "[OK]`t UpdateNpm : $NpmVersion to $NewNpmVersion;`n"
		}
		else {$StringInstallProcess += "[ERROR]`t UpdateNpm ;`n"}
		
		# Créer l'arborescence pour React Native et installer les paquets
		[void]([System.IO.Directory]::CreateDirectory($ReactNativePath))
		Set-Location $ReactNativePath | Write-Output 'Y' | cmd /c "$InstallPath\npx" react-native@latest init $nomProjet
	}
	else {$StringInstallProcess += "[ERROR]`t Prefix+Cache ; $NpmPrefix ; $NpmCache`n"}

#-------- FINALISATION -----------

	# Créer le fichier témoin et y écrire le suivi de l'installation, OU créer une entrée dans l'observateur d'événements
	Out-File -FilePath "C:\Windows\Logs\temoin_$PackageName.txt" -InputObject $StringInstallProcess
	if (-Not $?) {Write-Eventlog -LogName 'Application' -Source 'Application' -EntryType 'Information' -EventID '777' -Message $StringInstallProcess}