**Java Challenge hecho por Kurt Lobato**

Para levantar la aplicación con maven se debe correr el comando
```mvn spring-boot:run```

**Pruebas de rendimiento**

|Thread pool size|Average Response Time|Throughput    
|----|----|-----
|100|8499|6621         
|200|4608|12175         
|300|2620|19252         
|400|2483|21811         
|500|1815|27084         
|600|1853|27804         
|700|2149|23583         
|800|2129|23727         
|900|2125|23770         
|1000|2662|20344         

Las pruebas de rendimiento parecen indicar que el número óptimo para la thread pool es de 500 threads
Las pruebas fueron realizadas con JMeter configurado a 1000 users con 10 repeticiones, el tamaño de la heap para las pruebas se mantuvo en el default de 200mb
