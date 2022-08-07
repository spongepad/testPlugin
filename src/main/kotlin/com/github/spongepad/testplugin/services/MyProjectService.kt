package com.github.spongepad.testplugin.services

import com.intellij.openapi.project.Project
import com.github.spongepad.testplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
