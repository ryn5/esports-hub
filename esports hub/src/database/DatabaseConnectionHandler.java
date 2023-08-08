package database;

import model.*;
import tabs.AnalystSalesPanel;
import tabs.AnalystViewersPanel;
import utils.PrintablePreparedStatement;

import java.sql.*;
import java.util.ArrayList;


/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	// Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	// Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private Connection connection = null;

	public DatabaseConnectionHandler() {
        login("ora_dennis34", "a94349206");
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}


    // INSERT Statements
	public void insertGame(Game game) {
//        INSERT INTO Game VALUES (1, 1, 2, '10-OCT-22', 1)
		try {

            String query = "INSERT INTO Game VALUES (?, ?, ?, ?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, game.getgID());
            ps.setInt(2, game.getBtID());
            ps.setInt(3, game.getRtID());
            ps.setDate(4, game.getDay());
            ps.setInt(5, game.getaID());
            ps.executeUpdate();
			connection.commit();

            query = "INSERT INTO SeasonDates VALUES (?, ?)";
            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setDate(1, game.getDay());
            ps.setString(2, game.getSeason());
            ps.executeUpdate();
            connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

    public void addCasts(Integer gID, Integer cID, String lang) {
        try {
            String query = "INSERT INTO Casts VALUES (?, ?, ?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, cID);
            ps.setInt(2, gID);
            ps.setString(3, lang);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void addTicket(int ticketnum, int vID, int gID, int aId, int seatNum) {
        try {
            String query = "INSERT INTO Ticket VALUES (?, ?, ?, ?, ?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, ticketnum);
            if (vID >= 0) {
                ps.setInt(2, vID);
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, gID);
            ps.setInt(4, aId);
            ps.setInt(5, seatNum);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void addRoster(Roster r, String[] ids) {
        try {
            String query = "INSERT INTO Roster VALUES (?, ?, ?, ?, ?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, r.getTeamID());
            ps.setString(2, r.getSeason());
            ps.setInt(3, r.getYear());
            ps.setInt(4, r.getWins());
            ps.setInt(5, r.getLosses());
            ps.executeUpdate();
            for (String s: ids) {
                int id = Integer.parseInt(s);
                query = "INSERT INTO PartofRoster VALUES (?, ?, ? ,?)";
                PrintablePreparedStatement ps2 = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                ps2.setString(1, r.getSeason());
                ps2.setInt(2, r.getYear());
                ps2.setInt(3, r.getTeamID());
                ps2.setInt(4, id);
                ps2.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void addTeam(Integer id, String name) {
        try {
            String query = "INSERT INTO TEAM VALUES (?, ?, ?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setNull(3, Types.VARCHAR);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void addAchievement(Achievement a) {
        try {
            String query = "INSERT INTO Achievement VALUES (?, ?, ?, ?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, a.getSeason());
            ps.setInt(2, a.getYear());
            ps.setInt(3, a.getPlacement());
            ps.setInt(4, a.getTeamID());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void addTMRoster(int tmID, Roster r) {
        try {
            String query = "INSERT INTO partofroster VALUES (?, ?, ?, ?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, r.getSeason());
            ps.setInt(2, r.getYear());
            ps.setInt(3, r.getTeamID());
            ps.setInt(4, tmID);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void addArena(Arena a) {
        try {
            String query = "INSERT INTO Arena VALUES (?, ?, ?, ?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, a.getArenaID());
            ps.setString(2, a.getName());
            ps.setString(3, a.getCity());
            ps.setInt(4, a.getCapacity());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void addViewer(int newKey, String name) {
        try {
            String query = "INSERT INTO Viewer VALUES (?, ?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, newKey);
            ps.setString(2, name);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // DELETE Statements
    public void deleteGame(int gID) {
		try {
			String query = "DELETE FROM Game WHERE gID = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, gID);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Game " + gID + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

    // UPDATE Statement
    public void updateTeamOwner(String owner, int teamID) {
        try {
            String query = "UPDATE TEAM SET owner = ? WHERE tID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, owner);
            ps.setInt(2, teamID);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void bookTicket(int selectedTicketNum, int viewerID) {
        try {
            String query = "UPDATE Ticket " + "SET vID = " + viewerID + " WHERE ticketNum = " + selectedTicketNum;
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void unbookTicket(int ticketNum) {
        try {
            String query = "UPDATE Ticket " + "SET vID = NULL WHERE ticketNum = " + ticketNum;
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateRoster(String wls, int teamID, String season, int year, int wl) {
        try {
            String query = "UPDATE Roster SET " + wls + " = ? WHERE tID = ? AND season = ? and year = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, wl);
            ps.setInt(2, teamID);
            ps.setString(3, season);
            ps.setInt(4, year);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    // OTHER
    public ArrayList<Game> getGames(int i, String attr, String term) {
        ArrayList<Game> list = new ArrayList<>();
        try {
            String query;
            PrintablePreparedStatement ps;
            if (attr.equals("")) {
                query = "SELECT * FROM GAME ORDER BY day DESC FETCH FIRST ? ROWS ONLY";
                ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                ps.setInt(1, i);
            } else if (attr.equals("team")) {
                query = "SELECT * FROM GAME WHERE rtID = ? OR btID = ? ORDER BY day DESC FETCH FIRST ? ROWS ONLY";
                ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                ps.setInt(1, Integer.parseInt(term));
                ps.setInt(2, Integer.parseInt(term));
                ps.setInt(3, i);
            } else {
                query = "SELECT * FROM GAME WHERE " + attr + " = ? ORDER BY day DESC FETCH FIRST ? ROWS ONLY";
                ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//                ps.setString(1, attr);
                ps.setInt(1, Integer.parseInt(term));
                ps.setInt(2, i);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                query = "SELECT season FROM SeasonDates WHERE day = ?";
                ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                ps.setDate(1, rs.getDate("day"));
                ResultSet rs2 = ps.executeQuery();
                rs2.next();
                Game temp = new Game(rs.getInt("gID"), rs.getInt("rtID"), rs.getInt("btID"),
                rs.getDate("day"), rs.getInt("aID"), rs2.getString("season"), rs.getDate("day").getYear() + 1900);
                list.add(temp);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return list;
    }

    public ArrayList<String> getGameCasts(int gID) {
        ArrayList<String> languages = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT language FROM CASTS WHERE gid = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, gID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                languages.add(rs.getString("language"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw new RuntimeException(e.getMessage());
        }
        return languages;
    }

    public Integer getMaxKey(String key, String table) {
        Integer ans = 0;
        try {
            String query;
            PrintablePreparedStatement ps;
            query = "SELECT " + key + " FROM " + table + " ORDER BY " + key + " DESC FETCH FIRST 1 ROWS ONLY";
            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ans = rs.getInt(key);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return ans;
    }

    public ArrayList<Integer> getKeys(String key, String table) {
        ArrayList<Integer> ans = new ArrayList<>();
        try {
            String query;
            PrintablePreparedStatement ps;
            query = "SELECT " + key + " FROM " + table + " ORDER BY " + key;
            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ans.add(rs.getInt(key));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return ans;
    }

    public Arena getArena(int aID) {
        Arena arena = null;
        try {
            String query = "SELECT * FROM Arena WHERE aID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, aID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arena = new Arena(rs.getInt("aID"), rs.getString("name"), rs.getString("city"), rs.getInt("capacity"));
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return arena;
    }

    public Team getTeam(int tID) {
        Team team = null;
        try {
            String query = "SELECT * FROM Team WHERE tID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, tID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                team = new Team(rs.getInt("tID"), rs.getString("name"), rs.getString("owner"));
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return team;
    }

    public ArrayList<Player> getRosterPlayers(int tmID, String season, int year) {
        ArrayList<Player> players = new ArrayList<>();
        try {
            String query = "SELECT m.tmid, alias, position FROM Player s, TeamMember m WHERE s.tmid = m.tmid AND m.tmid IN (SELECT tmid FROM partofroster WHERE season = ? AND year = ? AND tid = ?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, season);
            ps.setInt(2, year);
            ps.setInt(3, tmID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Player temp = new Player(rs.getInt("tmID"), rs.getString("position"), rs.getString("alias"));
                players.add(temp);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw new RuntimeException(e.getMessage());
        }
        return players;
    }

    public ArrayList<Staff> getRosterStaff(int tID, String season, int year) {
        ArrayList<Staff> staff = new ArrayList<>();
        try {
            String query = "SELECT m.tmid, name, role FROM Staff s, TeamMember m WHERE s.tmid = m.tmid AND m.tmid IN (SELECT tmid FROM partofroster WHERE season = ? AND year = ? AND tid = ?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, season);
            ps.setInt(2, year);
            ps.setInt(3, tID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Staff temp = new Staff(rs.getInt("tmid"), rs.getString("name"), rs.getString("role") );
                staff.add(temp);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw new RuntimeException(e.getMessage());
        }
        return staff;
    }
    public ArrayList<String> getWinningTeam() {
        ArrayList<String> winteam = new ArrayList<>();
        try {
            String query = "SELECT tID as tID, SUM(wins) AS wintotal " +
                    "FROM Roster r " +
                    "GROUP BY tID " +
                    "HAVING SUM(r.wins) >= ALL (SELECT SUM(wins) FROM Roster GROUP BY tID)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                winteam.add(getTeam(rs.getInt("tID")).getName());
                winteam.add(String.valueOf(rs.getInt("wintotal")));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw new RuntimeException(e.getMessage());
        }
        return winteam;
    }
    public ArrayList<Roster> getRosters(int tID) {
        ArrayList<Roster> rosters = new ArrayList<>();
        try {
            String query = "SELECT * FROM Roster WHERE tID = ? ORDER BY year DESC, season ASC";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, tID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roster r = new Roster(rs.getInt("tID"), rs.getString("season"), rs.getInt("year"),
                        rs.getInt("wins"), rs.getInt("losses"));
                rosters.add(r);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw new RuntimeException(e.getMessage());
        }
        return rosters;
    }

    public ArrayList<Team> getTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        try {
            String query = "SELECT * FROM team ORDER BY name";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Team team = new Team(rs.getInt("tID"), rs.getString("name"), rs.getString("owner"));
                teams.add(team);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return teams;
    }









    public ArrayList<SalesStruct> getGameSales() {
        ArrayList<SalesStruct> gameSalesList = new ArrayList<>();
        try {
            String query = "SELECT Game.gID, day, Team1.name AS btName, Team2.name AS rtName, COUNT(ticketNum) AS totalViewers, SUM(price) AS totalSales " +
                    "FROM Game " +
                    "INNER JOIN Ticket ON Game.gID = Ticket.gID " +
                    "INNER JOIN Seat ON Ticket.aID = Seat.aID AND Ticket.seatNum = Seat.seatNum " +
                    "INNER JOIN Team Team1 ON Game.btID = Team1.tID " +
                    "INNER JOIN Team Team2 ON Game.rtID = Team2.tID " +
                    "WHERE Ticket.vID IS NOT NULL " +
                    "GROUP BY Game.gID, day, Team1.name, Team2.name " +
                    "ORDER BY totalViewers DESC, totalSales DESC";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AnalystSalesPanel.GameSalesStruct gameSales = new AnalystSalesPanel.GameSalesStruct(
                        rs.getString("rtName") + " vs. " + rs.getString("btName"),
                        rs.getDate("day"),
                        rs.getInt("totalViewers"),
                        rs.getInt("totalSales")
                );
                gameSalesList.add(gameSales);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return gameSalesList;
    }



    public ArrayList<SalesStruct> getTeamSales() {
        ArrayList<SalesStruct> teamSalesList = new ArrayList<>();
        try {
            String query =
                    "SELECT Team.name, COUNT(DISTINCT Game.gID) AS totalGames, COUNT(ticketNum) AS totalViewers, SUM(price) AS totalSales " +
                            "FROM Team " +
                            "INNER JOIN Game ON Game.btID = Team.tID OR Game.rtID = Team.tID " +
                            "INNER JOIN Ticket ON Game.gID = Ticket.gID " +
                            "INNER JOIN Seat ON Ticket.aID = Seat.aID AND Ticket.seatNum = Seat.seatNum " +
                            "INNER JOIN Team Team1 ON Game.btID = Team1.tID " +
                            "INNER JOIN Team Team2 ON Game.rtID = Team2.tID " +
                            "WHERE Ticket.vID IS NOT NULL " +
                            "GROUP BY Team.name " +
                            "HAVING COUNT(DISTINCT Game.gID) > 1 " +
                            "ORDER BY totalViewers DESC, totalSales DESC";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AnalystSalesPanel.TeamSalesStruct teamSales = new AnalystSalesPanel.TeamSalesStruct(
                        rs.getString("name"),
                        rs.getInt("totalGames"),
                        rs.getInt("totalViewers"),
                        rs.getInt("totalSales")
                );
                teamSalesList.add(teamSales);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return teamSalesList;
    }

    public ArrayList<SalesStruct> getArenaSales() {
        ArrayList<SalesStruct> arenaSalesList = new ArrayList<>();
        try {
            String query =
                    "SELECT Arena.name, Arena.city, COUNT(ticketNum) AS totalViewers, SUM(price) AS totalSales " +
                            "FROM Arena " +
                            "INNER JOIN Game ON Game.aID = Arena.aID " +
                            "INNER JOIN Ticket ON Game.gID = Ticket.gID " +
                            "INNER JOIN Seat ON Ticket.aID = Seat.aID AND Ticket.seatNum = Seat.seatNum " +
                            "WHERE Ticket.vID IS NOT NULL " +
                            "GROUP BY Arena.name, Arena.city " +
                            "ORDER BY totalViewers DESC, totalSales DESC";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AnalystSalesPanel.ArenaSalesStruct teamSales = new AnalystSalesPanel.ArenaSalesStruct(
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getInt("totalViewers"),
                        rs.getInt("totalSales")
                );
                arenaSalesList.add(teamSales);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return arenaSalesList;
    }

    public ArrayList<String> getAvailTickets(int gID) {
        ArrayList<String> availTickets = new ArrayList<>();
        try {
            String query = "SELECT seatNum, price " +
                    "FROM Ticket NATURAL JOIN Seat " +
                    "WHERE Ticket.gID = " + gID + " AND Ticket.vID IS NULL";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                availTickets.add("Seat " + rs.getInt("seatNum") + ": $" + rs.getDouble("price"));
                System.out.println("Added ticket: Seat " + rs.getInt("seatNum") + ": $" + rs.getDouble("price"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return availTickets;
    }

    public ArrayList<Integer> getAvailTicketNums(int gID) {
        ArrayList<Integer> ticketNums = new ArrayList<>();
        try {
            String query = "SELECT ticketNum " +
                    "FROM Ticket NATURAL JOIN Seat " +
                    "WHERE Ticket.gID = " + gID + " AND Ticket.vID IS NULL";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ticketNums.add(rs.getInt("ticketNum"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return ticketNums;
    }



    public Integer[] getYears() {
        ArrayList<Integer> yearList = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT year FROM Roster";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                yearList.add(rs.getInt("year"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return yearList.toArray(new Integer[0]);
    }

    public String[] getSeasons(Integer year) {
        ArrayList<String> seasonList = new ArrayList<>();
        String query = "SELECT DISTINCT season FROM Roster WHERE year = ?";

        try {
            PreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query);
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                seasonList.add(rs.getString("season"));
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return seasonList.toArray(new String[0]);
    }

    public ArrayList<RosterStruct> getRostersBySeasonYear(Integer year, String season) {
        ArrayList<RosterStruct> rosters = new ArrayList<>();
        String query = "SELECT * FROM Roster " +
                "INNER JOIN Team ON Team.tID = Roster.tID " +
                "WHERE year = ? AND season = ? " +
                "ORDER BY wins DESC";

        try {
            PreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query);
            ps.setInt(1, year);
            ps.setString(2, season);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                rosters.add(new RosterStruct(
                        rs.getString("name"),
                        season,
                        year,
                        rs.getInt("wins"),
                        rs.getInt("losses")
                ));
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return rosters;
    }
    public ArrayList<String> getViewerTickets(int viewerID) {
        ArrayList<String> viewerTickets = new ArrayList<>();
        try {
            String query = "SELECT ticketNum, BT.name AS btName, RT.name AS rtName, " +
                    "Arena.name AS arenaName, seatNum " +
                    "FROM Ticket NATURAL JOIN Seat " +
                    "INNER JOIN Game ON Game.gID = Ticket.gID " +
                    "INNER JOIN Arena ON Arena.aID = Game.aID " +
                    "INNER JOIN Team BT ON Game.btID = BT.tID " +
                    "INNER JOIN Team RT ON Game.rtID = RT.tID " +
                    "WHERE Ticket.vID = " + viewerID + " " +
                    "ORDER BY ticketNum ASC";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                viewerTickets.add("Ticket " + rs.getInt("ticketNum") + " | " +
                        rs.getString("btName") + " vs " + rs.getString("rtName") +
                        " at " + rs.getString("arenaName") + " | " +
                        "Seat " + rs.getInt("seatNum"));
                System.out.println("Ticket " + rs.getInt("ticketNum") + " | " +
                        rs.getString("btName") + " vs " + rs.getString("rtName") +
                        " at " + rs.getString("arenaName") + " | " +
                        "Seat " + rs.getInt("seatNum"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return viewerTickets;
    }

    public ArrayList<Integer> getViewerTicketNums(int viewerID) {
        ArrayList<Integer> ticketNums = new ArrayList<>();
        try {
            String query = "SELECT ticketNum FROM Ticket " +
                    "WHERE Ticket.vID = " + viewerID + " ORDER BY ticketNum ASC";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ticketNums.add(rs.getInt("ticketNum"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return ticketNums;
    }

    public ArrayList<Achievement> getTeamAchievements(int teamID) {
        ArrayList<Achievement> achievements = new ArrayList<>();
        String query = "SELECT * FROM Achievement " +
                "WHERE tID = ? " +
                "ORDER BY year DESC, Season";

        try {
            PreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query);
            ps.setInt(1, teamID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                achievements.add(new Achievement(rs.getString("season"), rs.getInt("year"), rs.getInt("placement"), rs.getInt("tID")));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return achievements;
    }

    public ArrayList<ViewerStatsStruct> getViewerStats(int setting, String settingConstraint) {
        ArrayList<ViewerStatsStruct> viewerList = new ArrayList<>();
        String query;
        PreparedStatement ps;

        try {
            if (setting == AnalystViewersPanel.OVERALL_SETTING) {
                query = "SELECT Viewer.name, COUNT(DISTINCT Game.gID) AS gamesWatched, SUM(price) AS moneySpent " +
                        "FROM Viewer " +
                        "INNER JOIN Ticket ON Viewer.vID = Ticket.vID " +
                        "INNER JOIN Game ON Game.gID = Ticket.gID " +
                        "INNER JOIN Seat ON Seat.aID = Ticket.aID AND Seat.seatNum = Ticket.seatNum " +
                        "GROUP BY Viewer.name " +
                        "ORDER BY gamesWatched DESC";
                ps = new PrintablePreparedStatement(connection.prepareStatement(query), query);
            } else if (setting == AnalystViewersPanel.TEAM_SETTING) {
                query = "SELECT Viewer.name, COUNT(DISTINCT Game.gID) AS gamesWatched, SUM(price) AS moneySpent " +
                        "FROM Viewer " +
                        "INNER JOIN Ticket ON Viewer.vID = Ticket.vID " +
                        "INNER JOIN Game ON Game.gID = Ticket.gID " +
                        "INNER JOIN Seat ON Seat.aID = Ticket.aID AND Seat.seatNum = Ticket.seatNum " +
                        "INNER JOIN Team ON Team.tID = Game.rtID OR Team.tID = Game.btID " +
                        "WHERE Team.name = ? " +
                        "GROUP BY Viewer.name " +
                        "ORDER BY gamesWatched DESC";
                ps = new PrintablePreparedStatement(connection.prepareStatement(query), query);
                ps.setString(1, settingConstraint);
            } else if (setting == AnalystViewersPanel.ARENA_SETTING) {
                query = "SELECT Viewer.name, COUNT(DISTINCT Game.gID) AS gamesWatched, SUM(price) AS moneySpent " +
                        "FROM Viewer " +
                        "INNER JOIN Ticket ON Viewer.vID = Ticket.vID " +
                        "INNER JOIN Game ON Game.gID = Ticket.gID " +
                        "INNER JOIN Seat ON Seat.aID = Ticket.aID AND Seat.seatNum = Ticket.seatNum " +
                        "INNER JOIN Arena ON Game.aID = Arena.aID " +
                        "WHERE Arena.name = ? " +
                        "GROUP BY Viewer.name " +
                        "ORDER BY gamesWatched DESC";
                ps = new PrintablePreparedStatement(connection.prepareStatement(query), query);
                ps.setString(1, settingConstraint);
            } else {
                query = "SELECT Viewer.name " +
                        "FROM Viewer " +
                        "WHERE NOT EXISTS (" +
                        "SELECT Game.gID " +
                        "FROM Game " +
                        "INNER JOIN Team ON Game.rtID = Team.tID OR Game.btID = Team.tID " +
                        "WHERE Team.name = ? " +
                        "MINUS " +
                        "SELECT Game.gID " +
                        "FROM Game " +
                        "INNER JOIN Team ON Game.rtID = Team.tID OR Game.btID = Team.tID " +
                        "INNER JOIN Ticket ON Ticket.gID = Game.gID " +
                        "WHERE Team.name = ? AND Viewer.vID = Ticket.vID)";
                ps = new PrintablePreparedStatement(connection.prepareStatement(query), query);
                ps.setString(1, settingConstraint);
                ps.setString(2, settingConstraint);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (setting == AnalystViewersPanel.SUPERFAN_SETTING) {
                    viewerList.add(new ViewerStatsStruct(
                            rs.getString("name"),
                            0,
                            0
                    ));
                } else {
                    viewerList.add(new ViewerStatsStruct(
                            rs.getString("name"),
                            rs.getInt("gamesWatched"),
                            rs.getInt("moneySpent")
                    ));
                }
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return viewerList;
    }







    public String[] getArenaNames() {
        ArrayList<String> names = new ArrayList<>();
        String query = "SELECT name FROM Arena";

        try {
            PreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return names.toArray(new String[0]);
    }




    public ArrayList<Arena> getArenas() {
        ArrayList<Arena> arenas = new ArrayList<>();
        String query = "SELECT * FROM Arena " +
                "ORDER BY aID ASC";
        try {
            PreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                arenas.add(new Arena(rs.getInt("aID"),
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getInt("capacity")));
            }
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return arenas;
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }
    //
    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }


    public ArrayList<Integer> getTeamMemberAttr(String table, String attr, int id) {
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            String query = "SELECT t.tmid FROM " + table +" p, TeamMember t WHERE p.tmid=t.tmid AND " + attr + " = ?";
            System.out.println(query);
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("tmid"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw new RuntimeException(e.getMessage());
        }
        return ids;
    }

    public ArrayList<String> getTeamMemberDescrip(int id, ArrayList<String> attrs, String setting) {
        ArrayList<String> stuff = new ArrayList<>();
        try {
            String query = "SELECT " ;
            for (int i = 0; i < attrs.size(); i++) {
                String temp = attrs.get(i);
                if (temp.equals("tmid"))
                    temp = "t.tmid";
                if (i == 0) {
                    query += temp;
                } else query += ", " + temp;
            }
            query += " FROM TeamMember t, " + setting + " p WHERE t.tmid = p.tmid AND t.tmid = ?";
            System.out.println(query);
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (String s : attrs) {
                    if (s.equals("t.tmid"))
                        stuff.add(String.valueOf(rs.getInt("tmid")));
                    else if (s.equals("age"))
                        stuff.add(String.valueOf(rs.getInt(s)));
                    else stuff.add(rs.getString(s));
                }

            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw new RuntimeException(e.getMessage());
        }
        return stuff;
    }
}
