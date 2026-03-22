package service.rayne.spring.intro.repository;

import service.rayne.spring.intro.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {
  private final DataSource dataSource;

  public JdbcMemberRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Member save(Member member) {
    String sql = "insert into member (name) values (?)";

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      connection = getConnection();
      preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      preparedStatement.setString(1, member.getName());

      preparedStatement.executeUpdate();
      resultSet = preparedStatement.getGeneratedKeys();

      if (resultSet.next()) member.setId(resultSet.getLong(1));
      else throw new SQLException("ID 조회 실패");

      return member;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      // close(connection, preparedStatement, resultSet);
    }
  }

  @Override
  public Optional<Member> findById(Long id) {
    String sql = "select * from member where id = ?";

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      connection = getConnection();
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setLong(1, id);

      resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        Member member = new Member();
        member.setId(resultSet.getLong("id"));
        member.setName(resultSet.getString("name"));
        return Optional.of(member);
      } else return Optional.empty();

    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(connection, preparedStatement, resultSet);
    }
  }

  @Override
  public Optional<Member> findByName(String name) {
    String sql = "select * from member where name = ?";

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      connection = getConnection();
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, name);

      resultSet = preparedStatement.executeQuery();

      if (resultSet.next()) {
        Member member = new Member();
        member.setId(resultSet.getLong("id"));
        member.setName(resultSet.getString("name"));
        return Optional.of(member);
      } else return Optional.empty();

    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(connection, preparedStatement, resultSet);
    }
  }

  @Override
  public List<Member> findAll() {
    String sql = "select * from member";

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
      connection = getConnection();
      preparedStatement = connection.prepareStatement(sql);

      resultSet = preparedStatement.executeQuery();

      List<Member> members = new ArrayList<>();
      while (resultSet.next()) {
        Member member = new Member();
        member.setId(resultSet.getLong("id"));
        member.setName(resultSet.getString("name"));
        members.add(member);
      }

      return members;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(connection, preparedStatement, resultSet);
    }
  }

  private Connection getConnection() throws SQLException {
    return DataSourceUtils.getConnection(dataSource);
  }

  private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
    try {
      if (resultSet != null) {
        resultSet.close();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.err);
    }

    try {
      if (preparedStatement != null) {
        preparedStatement.close();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.err);
    }

    try {
      if (connection != null) {
        close(connection);
      }
    } catch (SQLException e) {
      e.printStackTrace(System.err);
    }
  }

  private void close(Connection connection) throws SQLException {
    DataSourceUtils.releaseConnection(connection, dataSource);
  }
}
