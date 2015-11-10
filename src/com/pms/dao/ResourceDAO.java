package com.pms.dao;

import java.util.List;

import com.pms.model.ResData;
import com.pms.model.ResDataOrg;
import com.pms.model.ResDataTemplate;
import com.pms.model.ResFeature;
import com.pms.model.ResRole;
import com.pms.model.ResRoleOrg;
import com.pms.model.ResRoleResource;
import com.pms.model.ResRoleResourceImport;

public interface ResourceDAO {
	ResRoleResource ResRoleResourceAdd(ResRoleResource resRoleResource) throws Exception;
	ResFeature FeatureAdd(ResFeature feature) throws Exception;
	ResRoleOrg ResRoleOrgAdd(ResRoleOrg resRoleOrg) throws Exception;
	ResDataOrg ResDataOrgAdd(ResDataOrg resDataOrg) throws Exception;
	void FeatureDel(ResFeature feature) throws Exception;
	List<ResFeature> GetFeatureNodeByParentId(String pid, ResFeature criteria) throws Exception;
	boolean FeatureHasChild(String pid) throws Exception;
	int GetFeatureNodeCountByParentId(String pid) throws Exception;
	List<ResFeature> GetAllFeatures(ResFeature criteria) throws Exception;
	List<ResFeature> GetFeatures(String pid, ResFeature criteria, int page, int rows) throws Exception;
	int GetFeaturesCount(ResFeature criteria) throws Exception;
	
	ResData DataAdd(ResData data) throws Exception;
	void DataDel(ResData data) throws Exception;
	List<ResData> GetDatas(List<String> resource_status, List<String> resource_type, List<String> dataset_sensitive_level,
			List<String> data_set, List<String> section_class, List<String> element, List<String> section_relatioin_class, 
			ResData criteria, int page, int rows) throws Exception;
	int GetDatasCount(ResData criteria) throws Exception;
	List<ResDataTemplate> GetDataTemplates(List<String> resource_status, List<String> resource_type, List<String> dataset_sensitive_level,
			List<String> data_set, List<String> section_class, List<String> element, List<String> section_relatioin_class, 
			ResDataTemplate criteria, int page, int rows) throws Exception;
	int GetDataTemplatesCount(ResDataTemplate criteria) throws Exception;
	//ResData GetDataByColumn(String dataSet, String element) throws Exception;
	ResData GetDataByRelationRow(String dataSet, String element, String elemnetValue) throws Exception;
	ResData GetDataByRelationColumn(String dataSet, String sectionClass, String element) throws Exception;
	ResDataTemplate GetDataTemplateByRelationRow(String dataSet, String element, String elemnetValue) throws Exception;
	ResDataTemplate GetDataTemplateByRelationColumn(String dataSet, String sectionClass, String element) throws Exception;
	
	ResRole RoleAdd(ResRole role) throws Exception;
	ResRole RoleImport(ResRole role) throws Exception;
	void RoleDel(ResRole role) throws Exception;
	public List<ResRole> GetAllRoles() throws Exception;
	List<ResRole> GetRolesByTime(String time) throws Exception;	
	List<ResRole> GetRoles(ResRole criteria, int page, int rows) throws Exception;
	int GetRolesCount(ResRole criteria) throws Exception;
	
	void UpdateFeatureRoleResource(String roleId, List<String> featureIds) throws Exception;
	void UpdateDataRoleResource(String roleId, List<String> dataIds) throws Exception;
	void UpdateDataResource(List<String> dataIds) throws Exception;
	List<ResRoleResource> GetAllResRoles() throws Exception;
	List<ResRoleResource> GetResRolesByTime(String time) throws Exception;
	List<ResRoleResource> GetRoleResourcesByRoleid(String id) throws Exception;
	List<ResRole> GetRoleById(int id) throws Exception;
	ResFeature GetFeatureByResId(String id) throws Exception;
	List<ResFeature> GetFeatureById(int id) throws Exception;
	List<ResData> GetDataByResId(String resId) throws Exception;
	List<ResDataTemplate> GetDataTemplateByResId(String resId) throws Exception;
	List<ResData> GetDataById(int id) throws Exception;
	
	List<ResData> GetDatasByRole(String roleId) throws Exception;
	List<ResData> GetDatasByTime(String time) throws Exception;
	List<ResData> GetColumnDatasByDataSet(String dataSet) throws Exception;
	
	ResRoleResourceImport ResRoleResourceImportAdd(ResRoleResourceImport rrri) throws Exception; 
	List<ResRoleResourceImport> GetResRoleResourceImport() throws Exception;
	int ResRoleResourceImportClear() throws Exception;
	int ClearPublicRoleAndRelationship() throws Exception;

	List<ResRoleOrg> GetResRoleOrgByRoleid(String id) throws Exception;
	List<ResDataOrg> GetResDataOrgByResId(String id) throws Exception;
}
