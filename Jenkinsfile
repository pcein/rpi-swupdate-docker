#!/usr/bin/env groovy

pipeline {
    
    agent {
	docker {
	    image 'pcein/ubuntu_yocto'
	    args '--mount type=bind,source=/home/pramode/yocto-caches,target=/home/pramode/yocto-caches'
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


