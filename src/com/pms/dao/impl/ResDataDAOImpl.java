package com.pms.dao.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.pms.dao.ResDataDAO;
import com.pms.model.HibernateUtil;
import com.pms.model.ResData;
import com.pms.model.ResDataTemplate;
import com.pms.util.ConfigHelper;
import com.pms.util.DateTimeUtil;

public class ResDataDAOImpl implements ResDataDAO {
	
	private static Log logger = LogFactory.getLog(ResDataDAOImpl.class);
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public ResData ImportResDataOfColumnSave(ResData rd, String orgid)
//			throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		
//		rd.setDATA_VERSION(1);
//		
//		List<ResData> rs = null;
//		String sqlString = "select * from WA_AUTHORITY_DATA_RESOURCE where DATA_SET = :DATA_SET and ELEMENT = :ELEMENT and ELEMENT_VALUE is null ";
//		try {
//			Query q = session.createSQLQuery(sqlString).addEntity(ResData.class);
//			q.setString("DATA_SET", rd.getDATA_SET());
//			q.setString("ELEMENT", rd.getELEMENT());
//			rs = q.list();
//			
//			ResDataOrg rdo = null;
//			sqlString = "select * from WA_AUTHORITY_RESOURCE_ORG where RESOURCE_ID = :RESOURCE_ID ";
//			q = session.createSQLQuery(sqlString).addEntity(ResDataOrg.class);
//			for(int i = 0; i<rs.size(); i++) {
//				q.setString("RESOURCE_ID", rs.get(i).getRESOURCE_ID());
//				rdo = (ResDataOrg) q.uniqueResult();
//				if( rdo.getCLUE_DST_SYS().equals( orgid ) ) {
//					rd.setId(rs.get(i).getId());
//					rd.setRESOURCE_ID(rs.get(i).getRESOURCE_ID());
//					rd.setDATA_VERSION(rs.get(i).getDATA_VERSION() + 1);
//					break;
//				} else {
//					continue;
//				}
//			}
//						
//			String timenow = DateTimeUtil.GetCurrentTime();
//			rd.setLATEST_MOD_TIME(timenow);
//			
//			rd = (ResData) session.merge(rd);
//			if( rd.getRESOURCE_ID() == null || rd.getRESOURCE_ID().length() == 0 ) {
//				rd.setRESOURCE_ID( new Integer(rd.getId()).toString() );
//				session.merge(rd);
//			}
//			
//			if(rdo == null) {
//				rdo = new ResDataOrg();
//				rdo.setRESOURCE_ID(rd.getRESOURCE_ID());
//				rdo.setCLUE_DST_SYS(orgid);
//				rdo.setDELETE_STATUS(ResDataOrg.DELSTATUSNO);
//				rdo.setDATA_VERSION(1);
//				rdo.setLATEST_MOD_TIME(timenow);
//				session.merge(rdo);
//			}
//			tx.commit();
//		} catch(ConstraintViolationException cne){
//			tx.rollback();
//			System.out.println(cne.getSQLException().getMessage());
//			throw new Exception("存在重名资源。");
//		}
//		catch(org.hibernate.exception.SQLGrammarException e)
//		{
//			tx.rollback();
//			System.out.println(e.getSQLException().getMessage());
//			throw e.getSQLException();
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		}
//		finally
//		{
//			HibernateUtil.closeSession();
//		}
//		return rd;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public void ImportResDataOfRelationColumn(ResDataTemplate rdt) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		
		rdt.setDATA_VERSION(1);
		
		List<ResDataTemplate> rs = null;
		String sqlString = "select * from WA_AUTHORITY_DATA_RESOURCE_TEMPLATE where DATA_SET = :DATA_SET and ELEMENT = :ELEMENT and SECTION_CLASS = :SECTION_CLASS and ELEMENT_VALUE is null and resource_type=:resource_type ";
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(ResDataTemplate.class);
			q.setString("DATA_SET", rdt.getDATA_SET());
			q.setString("ELEMENT", rdt.getELEMENT());
			q.setString("SECTION_CLASS", rdt.getSECTION_CLASS());
			q.setInteger("resource_type", rdt.getResource_type());
			rs = q.list();
			
			if( rs != null && rs.size() > 1) {
				String warnMesg = "[IRD]duplicate record found when search data resource of column relation by condition of dataset:'" + rdt.getDATA_SET() + "', element:'" + rdt.getELEMENT() + "', sectionClass:'" + rdt.getSECTION_CLASS() + "' ";
				logger.info(warnMesg);
				throw new Exception(warnMesg);
			} else if ( rs != null && rs.size() == 1) {
				ResDataTemplate exist = rs.get(0);
				exist.setDELETE_STATUS(ResDataTemplate.DELSTATUSNO);
				session.merge(exist);
			} else {
				String timenow = DateTimeUtil.GetCurrentTime();
				rdt.setLATEST_MOD_TIME(timenow);
				
				rdt = (ResDataTemplate) session.merge(rdt);
				if( rdt.getRESOURCE_ID() == null || rdt.getRESOURCE_ID().length() == 0 ) {
					rdt.setRESOURCE_ID( String.format("%s%010d", ConfigHelper.getInstance().getRegion(), rdt.getId()) );
					session.merge(rdt);
				}
			}
			tx.commit();
		} catch(ConstraintViolationException cne){
			tx.rollback();
			System.out.println(cne.getSQLException().getMessage());
			throw new Exception("存在重名资源。");
		}
		catch(org.hibernate.exception.SQLGrammarException e)
		{
			tx.rollback();
			System.out.println(e.getSQLException().getMessage());
			throw e.getSQLException();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return;		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void ImportResDataOfRelationRow(ResDataTemplate rdt) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		
		rdt.setDATA_VERSION(1);
		
		List<ResDataTemplate> rs = null;
		String sqlString = "select * from WA_AUTHORITY_DATA_RESOURCE_TEMPLATE where DATA_SET = :DATA_SET and ELEMENT = :ELEMENT and ELEMENT_VALUE =:ELEMENT_VALUE and resource_type=:resource_type ";
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(ResDataTemplate.class);
			q.setString("DATA_SET", rdt.getDATA_SET());
			q.setString("ELEMENT", rdt.getELEMENT());
			q.setString("ELEMENT_VALUE", rdt.getELEMENT_VALUE());
			q.setInteger("resource_type", rdt.getResource_type());
			rs = q.list();
			
			if( rs != null && rs.size() > 1) {
				String warnMesg = "[IRD]duplicate record found when search data resource of row relation by condition of dataset:'" + rdt.getDATA_SET() + "', element:'" + rdt.getELEMENT() + "', element_value:'" + rdt.getELEMENT_VALUE() + "' ";
				logger.info(warnMesg);
				throw new Exception(warnMesg);
			} else if ( rs != null && rs.size() == 1) {
				ResDataTemplate exist = rs.get(0);
				exist.setDELETE_STATUS(ResDataTemplate.DELSTATUSNO);
				session.merge(exist);
			} else {
				String timenow = DateTimeUtil.GetCurrentTime();
				rdt.setLATEST_MOD_TIME(timenow);
				
				rdt = (ResDataTemplate) session.merge(rdt);
				if( rdt.getRESOURCE_ID() == null || rdt.getRESOURCE_ID().length() == 0 ) {
					rdt.setRESOURCE_ID( String.format("%s%010d", ConfigHelper.getInstance().getRegion(), rdt.getId()) );
					session.merge(rdt);
				}
			}
			tx.commit();
		} catch(ConstraintViolationException cne){
			tx.rollback();
			System.out.println(cne.getSQLException().getMessage());
			throw new Exception("存在重名资源。");
		}
		catch(org.hibernate.exception.SQLGrammarException e)
		{
			tx.rollback();
			System.out.println(e.getSQLException().getMessage());
			throw e.getSQLException();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void ImportResDataOfRelationClassify(ResDataTemplate rdt) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		
		rdt.setDATA_VERSION(1);
		
		List<ResDataTemplate> rs = null;
		String sqlString = "select * from WA_AUTHORITY_DATA_RESOURCE_TEMPLATE where DATA_SET = :DATA_SET and SECTION_RELATIOIN_CLASS = :SECTION_RELATIOIN_CLASS and resource_type=:resource_type";
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(ResDataTemplate.class);
			q.setString("DATA_SET", rdt.getDATA_SET());
			q.setString("SECTION_RELATIOIN_CLASS", rdt.getSECTION_RELATIOIN_CLASS());
			q.setInteger("resource_type", rdt.getResource_type());
			rs = q.list();
			
			if( rs != null && rs.size() > 1) {
				String warnMesg = "[IRD]duplicate record found when search data resource of classify relation by condition of dataset:'" + rdt.getDATA_SET() + "', sectionRelationClass:'" + rdt.getSECTION_RELATIOIN_CLASS() + "' ";
				logger.info(warnMesg);
				throw new Exception(warnMesg);
			} else if ( rs != null && rs.size() == 1) {
				ResDataTemplate exist = rs.get(0);
				exist.setDELETE_STATUS(ResDataTemplate.DELSTATUSNO);
				session.merge(exist);
			} else {
				String timenow = DateTimeUtil.GetCurrentTime();
				rdt.setLATEST_MOD_TIME(timenow);
				
				rdt = (ResDataTemplate) session.merge(rdt);
				if( rdt.getRESOURCE_ID() == null || rdt.getRESOURCE_ID().length() == 0 ) {
					rdt.setRESOURCE_ID( String.format("%s%010d", ConfigHelper.getInstance().getRegion(), rdt.getId()) );
					session.merge(rdt);
				}
			}
			tx.commit();
		} catch(ConstraintViolationException cne){
			tx.rollback();
			System.out.println(cne.getSQLException().getMessage());
			throw new Exception("存在重名资源。");
		}
		catch(org.hibernate.exception.SQLGrammarException e)
		{
			tx.rollback();
			System.out.println(e.getSQLException().getMessage());
			throw e.getSQLException();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return;
	}

	@Override
	public void UpdateResDataTemplateStatus(int status) throws Exception {
		//update wa_authority_data_resource_template set delete_status = 1;
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
				
		int rs = 0;
		String sqlString = "update wa_authority_data_resource_template set delete_status = :delete_status ";
		try {
			Query q = session.createSQLQuery(sqlString);
			q.setInteger("delete_status", status);
			rs = q.executeUpdate();
			
			logger.info("[IRD] convert resource_template's status to delete," + rs + "records changed.");
			tx.commit();
		} 
		catch(org.hibernate.exception.SQLGrammarException e)
		{
			tx.rollback();
			System.out.println(e.getSQLException().getMessage());
			throw e.getSQLException();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return;
	}

	@Override
	public void DeleteResDataByDeletedRecordsInResDataTemplate()
			throws Exception {
		// delete wa_authority_data_resource by id in records of wa_authority_data_resource_template which are deleted
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
				
		int rs = 0;
		String sqlString = "update wa_authority_data_resource set delete_status = :resdata where RESOURCE_ID in ( select resource_id from wa_authority_data_resource_template where delete_status = :resdatatemplate ) ";
		try {
			Query q = session.createSQLQuery(sqlString);
			q.setInteger("resdata", ResData.DELSTATUSYES);
			q.setInteger("resdatatemplate", ResDataTemplate.DELSTATUSYES);
			rs = q.executeUpdate();
			
			logger.info("[IRD] convert resource's status to delete," + rs + "records changed.");
			tx.commit();
		} 
		catch(org.hibernate.exception.SQLGrammarException e)
		{
			tx.rollback();
			System.out.println(e.getSQLException().getMessage());
			throw e.getSQLException();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			HibernateUtil.closeSession();
		}
		return;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<ResColumn> QueryAllColumn() throws Exception {
//		Session session = HibernateUtil.currentSession();
//		Transaction tx = session.beginTransaction();
//		
//		List<ResColumn> rs = null;
//		String sqlString = "select * from WA_COLUMN ";
//		try {
//			Query q = session.createSQLQuery(sqlString).addEntity(ResColumn.class);
//			rs = q.list();
//			tx.commit();
//		} catch(Exception e) {
//			e.printStackTrace();
//			tx.rollback();
//			System.out.println(e.getMessage());
//			throw e;
//		}
//		finally
//		{
//			HibernateUtil.closeSession();
//		}
//		return rs;
//	}

}
