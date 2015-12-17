package com.pms.service;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.pms.dao.AdminDAO;
import com.pms.dao.AuditLogDAO;
import com.pms.dao.AuditLogDescribeDao;
import com.pms.dao.impl.AdminDAOImpl;
import com.pms.dao.impl.AuditLogDAOImpl;
import com.pms.dao.impl.AuditLogDescribeDAOImpl;
import com.pms.dto.LogGroupItem;
import com.pms.dto.LogOrgItem;
import com.pms.dto.LogPrivItem;
import com.pms.dto.LogResItem;
import com.pms.dto.LogRoleItem;
import com.pms.dto.LogSystemItem;
import com.pms.dto.LogUserItem;
import com.pms.model.Admin;
import com.pms.model.AuditGroupLog;
import com.pms.model.AuditGroupLogDescribe;
import com.pms.model.AuditOrgLog;
import com.pms.model.AuditOrgLogDescribe;
import com.pms.model.AuditPrivLog;
import com.pms.model.AuditPrivLogDescribe;
import com.pms.model.AuditResLog;
import com.pms.model.AuditResLogDescribe;
import com.pms.model.AuditRoleLog;
import com.pms.model.AuditRoleLogDescribe;
import com.pms.model.AuditSystemLog;
import com.pms.model.AuditSystemLogDescribe;
import com.pms.model.AuditUserLog;
import com.pms.model.AuditUserLogDescribe;

public class AuditLogService {
	public int QueryUserLogItems(AuditUserLog criteria, int page, int rows, List<LogUserItem> items) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		List<AuditUserLog> res = dao.GetAllAuditUserLogs(criteria, page, rows );
		LogUserItem logUserItem = null;
		for(int i=0; i<res.size(); i++) {
			logUserItem = ConvertUserLogToListItem(res.get(i));
			items.add(logUserItem);
		}
		int total = QueryUserLogsCount( criteria );
		return total;
	}
	
	private int QueryUserLogsCount(AuditUserLog criteria) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		int count = dao.GetAuditUserLogsCount( criteria );
		return count;
	}
	
	public LogUserItem ConvertUserLogToListItem(AuditUserLog auditUserLog) throws Exception {
		LogUserItem item = new LogUserItem();
		item.setLogid(auditUserLog.getId());
		item.setAdminId(auditUserLog.getAdminId());
		item.setIpAddr(auditUserLog.getIpAddr());
		item.setFlag(auditUserLog.getFlag());
		item.setResult(auditUserLog.getResult());
		item.setLATEST_MOD_TIME(auditUserLog.getLATEST_MOD_TIME());
		
		AuditLogDescribeDao dao = new AuditLogDescribeDAOImpl();
		List<AuditUserLogDescribe> logdesc = dao.GetUserLogDescByLogId(auditUserLog.getId());
		for (int i = 0; i < logdesc.size(); i++) {
			item.setDesc(logdesc.get(i).getDescrib());
		}
		
		return item;
		
	}
	
	public int QueryOrgLogItems(AuditOrgLog criteria, int page, int rows, List<LogOrgItem> items) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		List<AuditOrgLog> res = dao.GetAllAuditOrgLogs(criteria, page, rows );
		LogOrgItem logOrgItem = null;
		for(int i=0; i<res.size(); i++) {
			logOrgItem = ConvertOrgLogToListItem(res.get(i));
			items.add(logOrgItem);
		}
		int total = QueryOrgLogsCount( criteria );
		return total;
	}
	
	private int QueryOrgLogsCount(AuditOrgLog criteria) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		int count = dao.GetAuditOrgLogsCount( criteria );
		return count;
	}
	
	public LogOrgItem ConvertOrgLogToListItem(AuditOrgLog auditOrgLog) throws Exception {
		LogOrgItem item = new LogOrgItem();
		item.setLogid(auditOrgLog.getId());
		item.setAdminId(auditOrgLog.getAdminId());
		item.setIpAddr(auditOrgLog.getIpAddr());
		item.setFlag(auditOrgLog.getFlag());
		item.setResult(auditOrgLog.getResult());
		item.setLATEST_MOD_TIME(auditOrgLog.getLATEST_MOD_TIME());
		
		AuditLogDescribeDao dao = new AuditLogDescribeDAOImpl();
		List<AuditOrgLogDescribe> logdesc = dao.GetOrgLogDescByLogId(auditOrgLog.getId());
		for (int i = 0; i < logdesc.size(); i++) {
			item.setDesc(logdesc.get(i).getDescrib());
		}
		
		return item;
		
	}
	
	public int QueryGroupLogItems(AuditGroupLog criteria, int page, int rows, List<LogGroupItem> items) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		List<AuditGroupLog> res = dao.GetAllAuditGroupLogs(criteria, page, rows );
		LogGroupItem logGroupItem = null;
		for(int i=0; i<res.size(); i++) {
			logGroupItem = ConvertGroupLogToListItem(res.get(i));
			items.add(logGroupItem);
		}
		int total = QueryGroupLogsCount( criteria );
		return total;
	}
	
	private int QueryGroupLogsCount(AuditGroupLog criteria) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		int count = dao.GetAuditGroupLogsCount( criteria );
		return count;
	}
	
	public LogGroupItem ConvertGroupLogToListItem(AuditGroupLog auditGroupLog) throws Exception {
		LogGroupItem item = new LogGroupItem();
		item.setLogid(auditGroupLog.getId());
		item.setAdminId(auditGroupLog.getAdminId());
		item.setIpAddr(auditGroupLog.getIpAddr());
		item.setFlag(auditGroupLog.getFlag());
		item.setResult(auditGroupLog.getResult());
		item.setLATEST_MOD_TIME(auditGroupLog.getLATEST_MOD_TIME());
		
		AuditLogDescribeDao dao = new AuditLogDescribeDAOImpl();
		List<AuditGroupLogDescribe> logdesc = dao.GetGroupLogDescByLogId(auditGroupLog.getId());
		for (int i = 0; i < logdesc.size(); i++) {
			item.setDesc(logdesc.get(i).getDescrib());
		}
		
		return item;
		
	}
	
	public int QueryRoleLogItems(AuditRoleLog criteria, int page, int rows, List<LogRoleItem> items) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		List<AuditRoleLog> res = dao.GetAllAuditRoleLogs(criteria, page, rows );
		LogRoleItem logRoleItem = null;
		for(int i=0; i<res.size(); i++) {
			logRoleItem = ConvertRoleLogToListItem(res.get(i));
			items.add(logRoleItem);
		}
		int total = QueryRoleLogsCount( criteria );
		return total;
	}
	
	private int QueryRoleLogsCount(AuditRoleLog criteria) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		int count = dao.GetAuditRoleLogsCount( criteria );
		return count;
	}
	
	public LogRoleItem ConvertRoleLogToListItem(AuditRoleLog auditRoleLog) throws Exception {
		LogRoleItem item = new LogRoleItem();
		item.setLogid(auditRoleLog.getId());
		item.setAdminId(auditRoleLog.getAdminId());
		item.setIpAddr(auditRoleLog.getIpAddr());
		item.setFlag(auditRoleLog.getFlag());
		item.setResult(auditRoleLog.getResult());
		item.setLATEST_MOD_TIME(auditRoleLog.getLATEST_MOD_TIME());
		
		AuditLogDescribeDao dao = new AuditLogDescribeDAOImpl();
		List<AuditRoleLogDescribe> logdesc = dao.GetRoleLogDescByLogId(auditRoleLog.getId());
		for (int i = 0; i < logdesc.size(); i++) {
			item.setDesc(logdesc.get(i).getDescrib());
		}
		
		return item;
		
	}
	
	public int QuerySystemLogItems(AuditSystemLog criteria, int page, int rows, List<LogSystemItem> items) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		List<AuditSystemLog> res = dao.GetAllAuditSystemLogs(criteria, page, rows );
		LogSystemItem logSystemItem = null;
		for(int i=0; i<res.size(); i++) {
			logSystemItem = ConvertSystemLogToListItem(res.get(i));
			items.add(logSystemItem);
		}
		int total = QuerySystemLogsCount( criteria );
		return total;
	}
	
	private int QuerySystemLogsCount(AuditSystemLog criteria) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		int count = dao.GetAuditSystemLogsCount( criteria );
		return count;
	}
	
	public LogSystemItem ConvertSystemLogToListItem(AuditSystemLog auditSystemLog) throws Exception {
		LogSystemItem item = new LogSystemItem();
		item.setLogid(auditSystemLog.getId());
		item.setAdminId(auditSystemLog.getAdminId());
		item.setIpAddr(auditSystemLog.getIpAddr());
		item.setFlag(auditSystemLog.getFlag());
		item.setResult(auditSystemLog.getResult());
		item.setLATEST_MOD_TIME(auditSystemLog.getLATEST_MOD_TIME());
		
		AuditLogDescribeDao dao = new AuditLogDescribeDAOImpl();
		List<AuditSystemLogDescribe> logdesc = dao.GetSystemLogDescByLogId(auditSystemLog.getId());
		for (int i = 0; i < logdesc.size(); i++) {
			item.setDesc(logdesc.get(i).getDescrib());
		}
		
		return item;
		
	}
	
	public int QueryResLogItems(AuditResLog criteria, int page, int rows, List<LogResItem> items) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		List<AuditResLog> res = dao.GetAllAuditResLogs(criteria, page, rows );
		LogResItem logResItem = null;
		for(int i=0; i<res.size(); i++) {
			logResItem = ConvertResLogToListItem(res.get(i));
			items.add(logResItem);
		}
		int total = QueryResLogsCount( criteria );
		return total;
	}
	
	private int QueryResLogsCount(AuditResLog criteria) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		int count = dao.GetAuditResLogsCount( criteria );
		return count;
	}
	
	public LogResItem ConvertResLogToListItem(AuditResLog auditResLog) throws Exception {
		LogResItem item = new LogResItem();
		item.setLogid(auditResLog.getId());
		item.setAdminId(auditResLog.getAdminId());
		item.setIpAddr(auditResLog.getIpAddr());
		item.setFlag(auditResLog.getFlag());
		item.setResult(auditResLog.getResult());
		item.setLATEST_MOD_TIME(auditResLog.getLATEST_MOD_TIME());
		
		AuditLogDescribeDao dao = new AuditLogDescribeDAOImpl();
		List<AuditResLogDescribe> logdesc = dao.GetResLogDescByLogId(auditResLog.getId());
		for (int i = 0; i < logdesc.size(); i++) {
			item.setDesc(logdesc.get(i).getDescrib());
		}
		
		return item;
		
	}
	
	public int QueryPrivLogItems(AuditPrivLog criteria, int page, int rows, List<LogPrivItem> items) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		List<AuditPrivLog> res = dao.GetAllAuditPrivLogs(criteria, page, rows );
		LogPrivItem logPrivItem = null;
		for(int i=0; i<res.size(); i++) {
			logPrivItem = ConvertPrivLogToListItem(res.get(i));
			items.add(logPrivItem);
		}
		int total = QueryPrivLogsCount( criteria );
		return total;
	}
	
	private int QueryPrivLogsCount(AuditPrivLog criteria) throws Exception {
		AuditLogDAO dao = new AuditLogDAOImpl();
		int count = dao.GetAuditPrivLogsCount( criteria );
		return count;
	}
	
	public LogPrivItem ConvertPrivLogToListItem(AuditPrivLog auditPrivLog) throws Exception {
		LogPrivItem item = new LogPrivItem();
		item.setLogid(auditPrivLog.getId());
		item.setAdminId(auditPrivLog.getAdminId());
		item.setIpAddr(auditPrivLog.getIpAddr());
		item.setFlag(auditPrivLog.getFlag());
		item.setResult(auditPrivLog.getResult());
		item.setLATEST_MOD_TIME(auditPrivLog.getLATEST_MOD_TIME());
		
		AuditLogDescribeDao dao = new AuditLogDescribeDAOImpl();
		List<AuditPrivLogDescribe> logdesc = dao.GetPrivLogDescByLogId(auditPrivLog.getId());
		for (int i = 0; i < logdesc.size(); i++) {
			item.setDesc(logdesc.get(i).getDescrib());
		}
		
		return item;
		
	}
	
	public int adminLogin() throws Exception {
		AdminDAO dao = new AdminDAOImpl();
		ActionContext ctx = ActionContext.getContext();
		String user=(String) ctx.getSession().get("admin");
		Admin admin = null;
		admin = dao.GetAdminByLoginid(user);
		
		return admin.getId();
	}
}
