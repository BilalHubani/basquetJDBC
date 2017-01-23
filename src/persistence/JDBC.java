package persistence;

import model.Equipo;
import model.Jugador;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dam on 20/1/17.
 */
public class JDBC {
    private Connection connection;
    public void conectar() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/basket";
        String usr = "root";
        String pass = "";
        connection = DriverManager.getConnection(url, usr, pass);
    }
    public void desconectar() throws SQLException{
        if(connection != null){
            connection.close();
        }
    }
    public void insertarEquipo(Equipo equipo) throws SQLException{
        String insert = "insert into team values (?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(insert);
        ps.setString(1, equipo.getNombre());
        ps.setString(2, equipo.getLocalidad());
        ps.setDate(3, java.sql.Date.valueOf(equipo.getFecha()));
        ps.executeUpdate();
        ps.close();
    }
    public void insertarJugador(Jugador jugador) throws SQLException{
        String insert = "insert into player values (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(insert);
        ps.setString(1, jugador.getNombre());
        ps.setDate(2, java.sql.Date.valueOf(jugador.getNacimiento()));
        ps.setInt(3, jugador.getCanastas());
        ps.setInt(4, jugador.getAsistencias());
        ps.setInt(5, jugador.getRebote());
        ps.setString(6, jugador.getPosicion());
        ps.setString(7, jugador.getEquipo().getNombre());
        ps.executeUpdate();
        ps.close();
    }
    public void borrarJugador(String nombre) throws SQLException{
        String delete = "delete from player where name = '" + nombre+"';";
        Statement st = connection.createStatement();
        st.executeUpdate(delete);
        st.close();
    }
    public void actualizarJugador(Jugador jugador) throws SQLException{
        String update = "update player set name=?, birth=?, nbaskets=?, nassists=?, nrebounds=?, position=?, team=? where name = '" + jugador.getNombre()+"';";
        PreparedStatement ps = connection.prepareStatement(update);
        ps.setString(1, jugador.getNombre());
        ps.setDate(2, java.sql.Date.valueOf(jugador.getNacimiento()));
        ps.setInt(3, jugador.getCanastas());
        ps.setInt(4, jugador.getAsistencias());
        ps.setInt(5, jugador.getRebote());
        ps.setString(6, jugador.getPosicion());
        ps.setString(7, jugador.getEquipo().getNombre());
        ps.executeUpdate();
        ps.close();
    }
    public void actualizarEquipo(Equipo equipo) throws SQLException{
        String update = "update team set name=?, city=?, creation=? where name = '" + equipo.getNombre()+"';";
        PreparedStatement ps = connection.prepareStatement(update);
        ps.setString(1, equipo.getNombre());
        ps.setString(2, equipo.getLocalidad());
        ps.setDate(3, java.sql.Date.valueOf(equipo.getFecha()));
        ps.executeUpdate();
        ps.close();
    }
    public void actualizarJugadorEquipo(String nombre, Equipo equipo) throws SQLException{
        String update = "update player set  team=? where name = '" + nombre+"';";
        PreparedStatement ps = connection.prepareStatement(update);
        ps.setString(1, equipo.getNombre());
        ps.executeUpdate();
        ps.close();
    }
    public Jugador devolverNombreJugador(String nombre) throws SQLException{
        Jugador jugador = new Jugador();
        String query = "select * from player where name ='"+nombre+"';";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
            jugador.setNombre(rs.getString("name"));
            jugador.setNacimiento(rs.getDate("birth").toLocalDate());
            jugador.setCanastas(rs.getInt("nbaskets"));
            jugador.setAsistencias(rs.getInt("nassists"));
            jugador.setRebote(rs.getInt("nrebounds"));
            jugador.setPosicion(rs.getString("position"));
            Equipo equipo = new Equipo(rs.getString("team"));
            jugador.setEquipo(equipo);
        }
        rs.close();
        st.close();
        return jugador;
    }
    public List<Jugador> llenarListJugadores(ResultSet rs) throws SQLException{
        List<Jugador>jugadores = new ArrayList<>();
        while(rs.next()){
            Jugador jugador = new Jugador();
            Equipo equipo = new Equipo();
            jugador.setNombre(rs.getString("name"));
            jugador.setNacimiento(rs.getDate("birth").toLocalDate());
            jugador.setCanastas(rs.getInt("nbaskets"));
            jugador.setAsistencias(rs.getInt("nassists"));
            jugador.setRebote(rs.getInt("nrebounds"));
            jugador.setPosicion(rs.getString("position"));
            equipo.setNombre(rs.getString("team"));
            jugador.setEquipo(equipo);
            jugadores.add(jugador);
        }
        return jugadores;
    }
    public List<Jugador> devolverNombreJugdorIncompleto(String name) throws SQLException{
        List<Jugador> jugadores = new ArrayList<>();
        String query = "select * from player where name LIKE '%"+name+"%';";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        return llenarListJugadores(rs);
    }
    public List<Jugador> devolverCanastas(int baskets) throws SQLException{
        List<Jugador> jugadores = new ArrayList<>();
        String query = "select * from player where nbaskets>="+baskets+";";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        return llenarListJugadores(rs);
    }
    public List<Jugador> devolverAsistencias(int assists1, int assists2) throws SQLException{
        List<Jugador> jugadores = new ArrayList<>();
        String query = "select * from player where nassists>="+assists1+" && nassists<="+assists2+";";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        return llenarListJugadores(rs);
    }
    public List<Jugador> devolverPosicion(String position) throws SQLException{
        List<Jugador> jugadores = new ArrayList<>();
        String query = "select * from player where position='"+position+"';";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        return llenarListJugadores(rs);
    }
    public List<Jugador> devolverJugadoresNacidosAntesDe(LocalDate fecha) throws SQLException{
        List<Jugador> jugadores = new ArrayList<>();
        String query = "select * from player where birth<'"+java.sql.Date.valueOf(fecha)+"';";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        return llenarListJugadores(rs);
    }
    public List<String> agruparJugadoresDevolverEstadisticas() throws SQLException{
        List<String> resultado = new ArrayList<>();
        String query = "select position, max(nbaskets) as 'max1', min(nbaskets) as 'min1', avg(nbaskets) as 'avg1', max(nassists) as 'max2', min(nassists) as 'min2', avg(nassists) as 'avg2', max(nrebounds) as 'max3', min(nrebounds) as 'min3', avg(nrebounds) as 'avg3' from player group by position;";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                resultado.add("\n"+rs.getString("position")+": máximo canastas "+rs.getInt("max1")+ ", mínimo canastas "+rs.getInt("min1")+" y AVG canastas "+rs.getDouble("avg1")+
                        "\n"+": máximo asistencias "+rs.getInt("max2")+ ", mínimo asistencias "+rs.getInt("min2")+" y AVG asistencias "+rs.getDouble("avg2")+
                        "\n"+": máximo rebotes "+rs.getInt("max3")+ ", mínimo rebotes "+rs.getInt("min3")+"y AVG rebotes "+rs.getDouble("avg3"));
            }

        return resultado;
    }
    public List<String> devolverRanking() throws SQLException{
        List<String> ranking = new ArrayList<>();
        String query = "select name, nbaskets  from player order by nbaskets desc;";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        int num = 1;
        while(rs.next()){
            ranking.add(num+" "+rs.getString("name")+" "+rs.getInt("nbaskets"));
            num++;
        }
        return ranking;
    }
    public int devolverRankingPorPosicion(String nombre) throws SQLException{
        int posicion = 0;
        List<String> ranking = devolverRanking();
        Map<String, String> jugadores = new HashMap<>();
        for(int i=0; i<ranking.size(); i++){
            String[] jugador = ranking.get(i).split(" ");
            jugadores.put(jugador[1], jugador[0]);
        }
        if(jugadores.containsKey(nombre)){
            posicion = Integer.parseInt(jugadores.get(nombre));
        }
        return posicion;
    }
    public List<Equipo> devolverEquipoCiudad(String ciudad) throws SQLException{
        List<Equipo> equipos = new ArrayList<>();
        String query = "select * from team where city='"+ciudad+"';";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
            Equipo equipo = new Equipo();
            equipo.setNombre(rs.getString("name"));
            equipo.setLocalidad(rs.getString("city"));
            equipo.setFecha(rs.getDate("creation").toLocalDate());
            equipos.add(equipo);
        }
        return equipos;
    }
    public List<Jugador> devolverJugadoresEquipo(String equipo) throws SQLException{
        List<Jugador> jugadores = new ArrayList<>();
        String query = "select * from player where team='"+equipo+"';";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        return llenarListJugadores(rs);
    }
    public List<Jugador> devolverPosicionJugadoresEquipo(String equipo, String posicion) throws SQLException{
        List<Jugador> jugadores = new ArrayList<>();
        String query = "select * from player where team='"+equipo+"' and position='"+posicion+"';";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        return llenarListJugadores(rs);
    }
    public Jugador devolverJugadorMasCanastasEquipo(String equipo) throws SQLException{
        Jugador jugador = new Jugador();
        String query = "select * from player where team='"+equipo+"' and nbaskets=(select max(nbaskets) from player where team='"+equipo+"');";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()){
            Equipo equipo2 = new Equipo();
            jugador.setNombre(rs.getString("name"));
            jugador.setNacimiento(rs.getDate("birth").toLocalDate());
            jugador.setCanastas(rs.getInt("nbaskets"));
            jugador.setAsistencias(rs.getInt("nassists"));
            jugador.setRebote(rs.getInt("nrebounds"));
            jugador.setPosicion(rs.getString("position"));
            equipo2.setNombre(rs.getString("team"));
            jugador.setEquipo(equipo2);
        }
        return jugador;
    }
    public void eliminarJugadores() throws SQLException{
        String delete = "delete from player;";
        Statement st = connection.createStatement();
        st.executeUpdate(delete);
        delete = "delete from team;";
        st = connection.createStatement();
        st.executeUpdate(delete);
        st.close();
    }
}
