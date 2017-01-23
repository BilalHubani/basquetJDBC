package basquet;

import model.Equipo;
import model.Jugador;
import persistence.JDBC;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by dam on 20/1/17.
 */
public class basquet {
    public static void main(String[] args) throws SQLException {
        JDBC gestor = new JDBC();
        try{
            gestor.conectar();
            System.out.println("Estableciendo conexión");
            gestor.eliminarJugadores();
            System.out.println("");
            System.out.println("Creando equipos...");
            Equipo equipo1 = new Equipo("Team Mystic", LocalDate.of(2016, 5 , 10),"Alola");
            Equipo equipo2 = new Equipo("Team Valor", LocalDate.of(2015, 5 , 10), "Jhoto");
            System.out.println("");
            System.out.println("Equipos creados");
            System.out.println("");
            System.out.println("Insertando equipos...");
            gestor.insertarEquipo(equipo1);
            gestor.insertarEquipo(equipo2);
            System.out.println("Equipos insertados correctamente");
            System.out.println("");
            Jugador jugador1 = new Jugador(1,"Articuno", LocalDate.of(1993,5, 14), 120, 160, 60, "Pivot", equipo1);
            Jugador jugador2 = new Jugador(2,"Pikachu", LocalDate.of(1994,10, 25), 80, 32, 210, "Base", equipo1);
            Jugador jugador3 = new Jugador(3,"Vaporeon", LocalDate.of(1996,8,19), 110, 100, 120, "Alero", equipo1);
            Jugador jugador4 = new Jugador(4,"Moltres", LocalDate.of(1994, 7, 19), 110, 150, 50, "Alero", equipo2);
            Jugador jugador5 = new Jugador(5,"Charizard", LocalDate.of(1994, 9, 21), 70, 130, 40, "Pivot", equipo2);
            Jugador jugador6 = new Jugador(6,"Arcanine", LocalDate.of(1997, 3, 12), 100, 60, 150, "Base", equipo2);
            System.out.println("Insertando jugadores...");
            gestor.insertarJugador(jugador1);
            gestor.insertarJugador(jugador2);
            gestor.insertarJugador(jugador3);
            gestor.insertarJugador(jugador4);
            gestor.insertarJugador(jugador5);
            gestor.insertarJugador(jugador6);
            System.out.println("Jugadores insertados correctamente");
            System.out.println("");
            System.out.println("Modificando jugador Pikachu:");
            jugador2 = new Jugador(2,"Pikachu", LocalDate.of(1994,10, 25), 300, 50, 450, "Base", equipo1);
            gestor.actualizarJugador(jugador2);
            System.out.println("Jugador modificado correctamente");
            System.out.println("");
            System.out.println("Modificando Team Mystic:");
            equipo1 = new Equipo("Team Mystic", LocalDate.of(2015, 5 , 10), "Hoenn");
            gestor.actualizarEquipo(equipo1);
            System.out.println("Equipo modificado correctamente");
            System.out.println("");
            System.out.println("Modificando jugador Vaporeon de Team Mystic a Team Valor:");
            gestor.actualizarJugadorEquipo("Vaporeon", equipo2);
            System.out.println("Jugador modificado correctamente");
            System.out.println("");
            System.out.println("Borrar jugador Arcanine");
            gestor.borrarJugador("Arcanine");
            System.out.println("Jugador eliminado correctamente");
            System.out.println("");
            System.out.println("Devolver jugador Moltres");
            jugador1 = gestor.devolverNombreJugador("Moltres");
            System.out.println(jugador1);
            System.out.println("");
            System.out.println("Devolver los jugadores que tengan una 'r'");
            List<Jugador> jugadores = gestor.devolverNombreJugdorIncompleto("r");
            for(Jugador j: jugadores){
                System.out.println(j);
            }
            System.out.println("");
            System.out.println("Devolver los jugadores con más de 100 canastas");
            jugadores = gestor.devolverCanastas(100);
            for(Jugador j: jugadores){
                System.out.println(j);
            }
            System.out.println("");
            System.out.println("Devolver los jugadores con asistencias entre 50 y 120");
            jugadores = gestor.devolverAsistencias(50, 120);
            for(Jugador j: jugadores){
                System.out.println(j);
            }
            System.out.println("");
            System.out.println("Devolver los jugadores que tienen la posición de alero");
            jugadores = gestor.devolverPosicion("Alero");
            for(Jugador j: jugadores){
                System.out.println(j);
            }
            System.out.println("");
            System.out.println("Devolver los jugadores que hayan nacido antes del 10/10/1995");
            jugadores = gestor.devolverJugadoresNacidosAntesDe(LocalDate.of(1995,10,10));
            for(Jugador j: jugadores){
                System.out.println(j);
            }
            System.out.println("");
            System.out.println("Devolver max, min y avg de canastas, asistencias y rebotes agrupados por posición");
            Map<String,Double> maps = gestor.agruparJugadoresDevolverEstadisticas();
            System.out.println(maps);
            System.out.println("");
            System.out.println("Devolver ranking por número de canastas");
            List<String> strings = gestor.devolverRanking();
            for(String j: strings){
                System.out.println(j);
            }
            System.out.println("");
            System.out.println("Devolver la posición en el ranking de Moltres");
            int posicion = gestor.devolverRankingPorPosicion("Moltres");
            System.out.println("Moltres posicion:  "+posicion);
            System.out.println("");
            System.out.println("Listado de los equipos de Jhoto:");
            List<Equipo> equipos = gestor.devolverEquipoCiudad("Jhoto");
            for(Equipo e: equipos){
                System.out.println(e);
            }
            System.out.println("");
            System.out.println("Listado de jugadores del Team Valor");
            jugadores = gestor.devolverJugadoresEquipo("Team Valor");
            for(Jugador j: jugadores){
                System.out.println(j);
            }
            System.out.println("");
            System.out.println("Listado de jugadores del Team Mystic que sean Pivot");
            jugadores = gestor.devolverPosicionJugadoresEquipo("Team Mystic", "Pivot");
            for(Jugador j: jugadores){
                System.out.println(j);
            }
            System.out.println("");
            System.out.println("Jugador del Team Mystic con más canastas");
            jugador1 = gestor.devolverJugadorMasCanastasEquipo("Team Mystic");
            System.out.println(jugador1);
            System.out.println("");
        }catch(SQLException ex){
            System.out.println("Error con la BBDD: "+ex.getMessage());
        }finally{
            try{
                gestor.desconectar();
                System.out.println("Conexión cerrada");
            }catch (SQLException ex){
                System.out.println("Error al desconectar"+ex.getMessage());
            }
        }

        System.out.println("");

    }
    }

