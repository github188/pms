package com.pms.dao.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.pms.dao.OrganizationDAO;
import com.pms.model.HibernateUtil;
import com.pms.model.Organization;
import com.pms.model.OrganizationImport;

public class OrganizationDAOImpl implements OrganizationDAO {

	@Override
	public Organization OrgNodeAdd(Organization node) throws Exception {
		//打开线程安全的session对象
		Session session = HibernateUtil.currentSession();
		//打开事务
		Transaction tx = session.beginTransaction();
		try
		{
			node = (Organization) session.merge(node);
			tx.commit();
		}
		catch(ConstraintViolationException cne){
			tx.rollback();
			System.out.println(cne.getSQLException().getMessage());
			throw new Exception("存在重名机构。");
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
		return node;
	}

	@Override
	public void OrgNodeMod(Organization node) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		try
		{
			session.update(node);
			tx.commit();
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
	public void OrgNodeDel(Organization node) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		try
		{
			session.delete(node);
			tx.commit();
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
	public List<Organization> GetOrgNodeByParentId(String pid) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Organization> rs = null;
		String sqlString = "select * from WA_AUTHORITY_ORGNIZATION where PARENT_ORG = :PARENT_ORG and DELETE_STATUS =:DELETE_STATUS";
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Organization.class);
			q.setString("PARENT_ORG", pid);
			q.setInteger("DELETE_STATUS", Organization.DELSTATUSNO);
			rs = q.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@Override
	public boolean OrgHasChild(String pid) throws Exception {
		int rs = GetOrgNodeCountByParentId(pid);
		return rs > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> GetOrgNodeByParentId(String pid, int page, int rows)
			throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Organization> rs = null;
		String sqlString = "select * from WA_AUTHORITY_ORGNIZATION where PARENT_ORG = :PARENT_ORG and DELETE_STATUS =:DELETE_STATUS";
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Organization.class);
			q.setString("PARENT_ORG", pid);
			q.setInteger("DELETE_STATUS", Organization.DELSTATUSNO);
			q.setFirstResult((page-1) * rows);   
			q.setMaxResults(rows);
			rs = q.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return rs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> GetOrgNodeByParentId(String pid, Organization condition)
			throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Organization> rs = null;
		String sqlString = "select * from WA_AUTHORITY_ORGNIZATION where PARENT_ORG = :PARENT_ORG and DELETE_STATUS =:DELETE_STATUS";
		if( condition != null ) {
			if(condition.getUNIT() != null && condition.getUNIT().length() > 0) {
				sqlString += " and UNIT like :UNIT ";
			}
//			if(condition.getUid() != null && condition.getUid().length() > 0) {
//				sqlString += " and uid = :uid ";
//			}
			if(condition.getORG_LEVEL() != null && condition.getORG_LEVEL().length() > 0) {
				sqlString += " and ORG_LEVEL = :ORG_LEVEL ";
			}
		}
		
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Organization.class);
			q.setString("PARENT_ORG", pid);
			q.setInteger("DELETE_STATUS", Organization.DELSTATUSNO);
			if( condition != null ) {
				if(condition.getUNIT() != null && condition.getUNIT().length() > 0) {
					q.setString( "UNIT", "%" + condition.getUNIT() + "%" );
				}
//				if(condition.getUid() != null && condition.getUid().length() > 0) {
//					q.setString( "uid", condition.getUid() );
//				}
				if(condition.getORG_LEVEL() != null && condition.getORG_LEVEL().length() > 0) {
					q.setString( "ORG_LEVEL", condition.getORG_LEVEL() );
				}
			}
			rs = q.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@Override
	public int GetOrgNodeCountByParentId(String pid) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		int rs;
		String sqlString = "select count(*) from WA_AUTHORITY_ORGNIZATION where PARENT_ORG = :PARENT_ORG ";
		try {
			Query q = session.createSQLQuery(sqlString);
			q.setString("PARENT_ORG", pid);
			rs = ((BigInteger)q.uniqueResult()).intValue();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@Override
	public Organization GetOrgNodeById(String id) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Organization rs = null;
		String sqlString = "select * from WA_AUTHORITY_ORGNIZATION where GA_DEPARTMENT = :GA_DEPARTMENT ";
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Organization.class);
			q.setString("GA_DEPARTMENT", id);
			rs = (Organization) q.uniqueResult();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return rs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> GetOrgById(String id) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Organization> rs = null;
		String sqlString = "select * from WA_AUTHORITY_ORGNIZATION where GA_DEPARTMENT = :GA_DEPARTMENT ";
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Organization.class);
			q.setString("GA_DEPARTMENT", id);
			rs = q.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return rs;
	}
	
	@SuppressWarnings("unchecked")
	public List<Organization> GetAllOrgs() throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Organization> rs = null;
		String sqlString = "select * from WA_AUTHORITY_ORGNIZATION ";

		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Organization.class);
			
			rs = q.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@Override
	public OrganizationImport OrganizationImportSave(OrganizationImport oi)
			throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		try
		{
			oi = (OrganizationImport) session.merge(oi);
			tx.commit();
		}
		catch(ConstraintViolationException cne){
			tx.rollback();
			System.out.println(cne.getSQLException().getMessage());
			throw new Exception("存在重名待导入机构。");
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
		return oi;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationImport> GetOrgImports() throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<OrganizationImport> rs = null;
		String sqlString = "select * from WA_AUTHORITY_ORGNIZATION_IMPORT order by GA_DEPARTMENT";

		try {
			Query q = session.createSQLQuery(sqlString).addEntity(OrganizationImport.class);
			
			rs = q.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		return rs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void OrgImport(OrganizationImport oi) throws Exception {
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		List<Organization> rs = null;
		Organization node = null;
		String sqlString = "select * from WA_AUTHORITY_ORGNIZATION where GA_DEPARTMENT = :GA_DEPARTMENT ";
		try {
			Query q = session.createSQLQuery(sqlString).addEntity(Organization.class);
			q.setString("GA_DEPARTMENT", oi.getGA_DEPARTMENT());
			rs = q.list();
			
			if(rs == null || rs.size() == 0) {
				node = new Organization();
			} 
			else if (rs.size() == 1) {
				node = rs.get(0);
				if(oi.getUNIT().equals(node.getUNIT()) && oi.getORG_LEVEL().equals(node.getORG_LEVEL())
						&& oi.getPARENT_ORG().equals(node.getPARENT_ORG())) {
					return;
				}
			}
			else {
				throw new Exception("存在重名[" + oi.getGA_DEPARTMENT() + "]机构。");
			}
			
			node.setUNIT(oi.getUNIT());
			if(Organization.ROOTNODEID.equals(oi.getGA_DEPARTMENT())) {
				node.setPARENT_ORG("0");
			}
			else {
				node.setPARENT_ORG(oi.getPARENT_ORG());
			}
			node.setORG_LEVEL(oi.getORG_LEVEL());
			node.setGA_DEPARTMENT(oi.getGA_DEPARTMENT());
			node.setDATA_VERSION(node.getDATA_VERSION() + 1);
			node.setDELETE_STATUS(Organization.DELSTATUSNO);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.SIMPLIFIED_CHINESE);
			String timenow = sdf.format(new Date());
			node.setLATEST_MOD_TIME(timenow);
			
			session.merge(node);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			System.out.println(e.getMessage());
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
		
		return ;		
	}
}
