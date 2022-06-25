package learn.java.db.model;

import java.util.Date;

/**
 * Database Table Remarks:
 *   订单信息表
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table t_biz_order
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class TBizOrder {
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TBizOrder{");
        sb.append("id=").append(id);
        sb.append(", id-binary=").append(Long.toBinaryString(id));
        sb.append(", userId=").append(userId);
        sb.append(", state=").append(state);
        sb.append(", createTime=").append(createTime);
        sb.append(", createTime=").append(new Date(createTime));
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Database Column Remarks:
     *   自增主键;订单id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_biz_order.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   用户userId
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_biz_order.user_id
     *
     * @mbg.generated
     */
    private Long userId;

    /**
     * Database Column Remarks:
     *   订单状态;参考OrderStateEnum
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_biz_order.state
     *
     * @mbg.generated
     */
    private Integer state;

    /**
     * Database Column Remarks:
     *   下单时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_biz_order.create_time
     *
     * @mbg.generated
     */
    private Long createTime;

    /**
     * Database Column Remarks:
     *   更新时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_biz_order.update_time
     *
     * @mbg.generated
     */
    private Long updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_biz_order.id
     *
     * @return the value of t_biz_order.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_biz_order.id
     *
     * @param id the value for t_biz_order.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_biz_order.user_id
     *
     * @return the value of t_biz_order.user_id
     *
     * @mbg.generated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_biz_order.user_id
     *
     * @param userId the value for t_biz_order.user_id
     *
     * @mbg.generated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_biz_order.state
     *
     * @return the value of t_biz_order.state
     *
     * @mbg.generated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_biz_order.state
     *
     * @param state the value for t_biz_order.state
     *
     * @mbg.generated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_biz_order.create_time
     *
     * @return the value of t_biz_order.create_time
     *
     * @mbg.generated
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_biz_order.create_time
     *
     * @param createTime the value for t_biz_order.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_biz_order.update_time
     *
     * @return the value of t_biz_order.update_time
     *
     * @mbg.generated
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_biz_order.update_time
     *
     * @param updateTime the value for t_biz_order.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}