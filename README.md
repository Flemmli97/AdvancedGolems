# Advanced Golems 
[![](http://cf.way2muchnoise.eu/full_565062_Forge_%20.svg)![](http://cf.way2muchnoise.eu/versions/565062.svg)](https://www.curseforge.com/minecraft/mc-mods/advanced-golems)  
[![](http://cf.way2muchnoise.eu/full_565061_Fabric_%20.svg)![](http://cf.way2muchnoise.eu/versions/565061.svg)](https://www.curseforge.com/minecraft/mc-mods/advanced-golems-fabric)  
[![](https://img.shields.io/modrinth/dt/IaRtPu6Y?logo=modrinth&label=Modrinth)![](https://img.shields.io/modrinth/game-versions/IaRtPu6Y?logo=modrinth&label=Latest%20for)](https://modrinth.com/mod/advanced-golems)  
[![Discord](https://img.shields.io/discord/790631506313478155?color=0a48c4&label=discord)](https://discord.gg/8Cx26tfWNs)

Adds small golems to help you defend your base

To use this mod as a dependency add the following snippet to your build.gradle:  
```groovy
repositories {
    maven {
        name = "Flemmli97"
        url "https://gitlab.com/api/v4/projects/21830712/packages/maven"
    }
}

dependencies {    
    //Fabric/Loom==========    
    modImplementation("io.github.flemmli97:advancedgolems:${minecraft_version}-${mod_version}-${mod_loader}")
    
    //Forge==========    
    compile fg.deobf("io.github.flemmli97:advancedgolems:${minecraft_version}-${mod_version}-${mod_loader}")
}
```