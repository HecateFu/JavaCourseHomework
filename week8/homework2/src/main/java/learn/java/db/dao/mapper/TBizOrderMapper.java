package learn.java.db.dao.mapper;

import learn.java.db.model.TBizOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface TBizOrderMapper {

    int dropTable();

    int createTable();
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_biz_order
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 增加事务控制，保证插入数据和查询自增主键都使用写库
     */
    @Transactional
    int insert(TBizOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_biz_order
     *
     * @mbg.generated
     */
    int insertSelective(TBizOrder record);

    List<TBizOrder> select();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_biz_order
     *
     * @mbg.generated
     */
    TBizOrder selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_biz_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TBizOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_biz_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TBizOrder record);
}