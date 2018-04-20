#!/usr/bin/env groovy

pipeline {
    
    agent {
	docker {
	    image 'pcein/ubuntu_yocto'
	    args '-v /home/pramode/:/home/pramode'
	}
    }		

    stages {
        stage('Build') {
            steps {
                sh "#!/bin/bash \n" +
		   "./bake"
            }
        }
    }
}


