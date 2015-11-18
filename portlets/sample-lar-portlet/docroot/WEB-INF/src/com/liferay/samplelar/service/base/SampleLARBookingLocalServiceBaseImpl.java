/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.samplelar.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.persistence.ClassNamePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.exportimport.lar.ExportImportHelperUtil;
import com.liferay.portlet.exportimport.lar.ManifestSummary;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import com.liferay.samplelar.model.SampleLARBooking;
import com.liferay.samplelar.service.SampleLARBookingLocalService;
import com.liferay.samplelar.service.persistence.SampleLARBookingPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the sample l a r booking local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.samplelar.service.impl.SampleLARBookingLocalServiceImpl}.
 * </p>
 *
 * @author Mate Thurzo
 * @see com.liferay.samplelar.service.impl.SampleLARBookingLocalServiceImpl
 * @see com.liferay.samplelar.service.SampleLARBookingLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class SampleLARBookingLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements SampleLARBookingLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.samplelar.service.SampleLARBookingLocalServiceUtil} to access the sample l a r booking local service.
	 */

	/**
	 * Adds the sample l a r booking to the database. Also notifies the appropriate model listeners.
	 *
	 * @param sampleLARBooking the sample l a r booking
	 * @return the sample l a r booking that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SampleLARBooking addSampleLARBooking(
		SampleLARBooking sampleLARBooking) {
		sampleLARBooking.setNew(true);

		return sampleLARBookingPersistence.update(sampleLARBooking);
	}

	/**
	 * Creates a new sample l a r booking with the primary key. Does not add the sample l a r booking to the database.
	 *
	 * @param sampleLARBookingId the primary key for the new sample l a r booking
	 * @return the new sample l a r booking
	 */
	@Override
	public SampleLARBooking createSampleLARBooking(long sampleLARBookingId) {
		return sampleLARBookingPersistence.create(sampleLARBookingId);
	}

	/**
	 * Deletes the sample l a r booking with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sampleLARBookingId the primary key of the sample l a r booking
	 * @return the sample l a r booking that was removed
	 * @throws PortalException if a sample l a r booking with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SampleLARBooking deleteSampleLARBooking(long sampleLARBookingId)
		throws PortalException {
		return sampleLARBookingPersistence.remove(sampleLARBookingId);
	}

	/**
	 * Deletes the sample l a r booking from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sampleLARBooking the sample l a r booking
	 * @return the sample l a r booking that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SampleLARBooking deleteSampleLARBooking(
		SampleLARBooking sampleLARBooking) {
		return sampleLARBookingPersistence.remove(sampleLARBooking);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(SampleLARBooking.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return sampleLARBookingPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.samplelar.model.impl.SampleLARBookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return sampleLARBookingPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.samplelar.model.impl.SampleLARBookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return sampleLARBookingPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return sampleLARBookingPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return sampleLARBookingPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public SampleLARBooking fetchSampleLARBooking(long sampleLARBookingId) {
		return sampleLARBookingPersistence.fetchByPrimaryKey(sampleLARBookingId);
	}

	/**
	 * Returns the sample l a r booking matching the UUID and group.
	 *
	 * @param uuid the sample l a r booking's UUID
	 * @param groupId the primary key of the group
	 * @return the matching sample l a r booking, or <code>null</code> if a matching sample l a r booking could not be found
	 */
	@Override
	public SampleLARBooking fetchSampleLARBookingByUuidAndGroupId(String uuid,
		long groupId) {
		return sampleLARBookingPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the sample l a r booking with the primary key.
	 *
	 * @param sampleLARBookingId the primary key of the sample l a r booking
	 * @return the sample l a r booking
	 * @throws PortalException if a sample l a r booking with the primary key could not be found
	 */
	@Override
	public SampleLARBooking getSampleLARBooking(long sampleLARBookingId)
		throws PortalException {
		return sampleLARBookingPersistence.findByPrimaryKey(sampleLARBookingId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.samplelar.service.SampleLARBookingLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(SampleLARBooking.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("sampleLARBookingId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(com.liferay.samplelar.service.SampleLARBookingLocalServiceUtil.getService());
		indexableActionableDynamicQuery.setClass(SampleLARBooking.class);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"sampleLARBookingId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.samplelar.service.SampleLARBookingLocalServiceUtil.getService());
		actionableDynamicQuery.setClass(SampleLARBooking.class);
		actionableDynamicQuery.setClassLoader(getClassLoader());

		actionableDynamicQuery.setPrimaryKeyPropertyName("sampleLARBookingId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType,
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType,
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(dynamicQuery,
						"modifiedDate");
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<SampleLARBooking>() {
				@Override
				public void performAction(SampleLARBooking sampleLARBooking)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						sampleLARBooking);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(SampleLARBooking.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return sampleLARBookingLocalService.deleteSampleLARBooking((SampleLARBooking)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return sampleLARBookingPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the sample l a r bookings matching the UUID and company.
	 *
	 * @param uuid the UUID of the sample l a r bookings
	 * @param companyId the primary key of the company
	 * @return the matching sample l a r bookings, or an empty list if no matches were found
	 */
	@Override
	public List<SampleLARBooking> getSampleLARBookingsByUuidAndCompanyId(
		String uuid, long companyId) {
		return sampleLARBookingPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of sample l a r bookings matching the UUID and company.
	 *
	 * @param uuid the UUID of the sample l a r bookings
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of sample l a r bookings
	 * @param end the upper bound of the range of sample l a r bookings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching sample l a r bookings, or an empty list if no matches were found
	 */
	@Override
	public List<SampleLARBooking> getSampleLARBookingsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SampleLARBooking> orderByComparator) {
		return sampleLARBookingPersistence.findByUuid_C(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	 * Returns the sample l a r booking matching the UUID and group.
	 *
	 * @param uuid the sample l a r booking's UUID
	 * @param groupId the primary key of the group
	 * @return the matching sample l a r booking
	 * @throws PortalException if a matching sample l a r booking could not be found
	 */
	@Override
	public SampleLARBooking getSampleLARBookingByUuidAndGroupId(String uuid,
		long groupId) throws PortalException {
		return sampleLARBookingPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the sample l a r bookings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.samplelar.model.impl.SampleLARBookingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sample l a r bookings
	 * @param end the upper bound of the range of sample l a r bookings (not inclusive)
	 * @return the range of sample l a r bookings
	 */
	@Override
	public List<SampleLARBooking> getSampleLARBookings(int start, int end) {
		return sampleLARBookingPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of sample l a r bookings.
	 *
	 * @return the number of sample l a r bookings
	 */
	@Override
	public int getSampleLARBookingsCount() {
		return sampleLARBookingPersistence.countAll();
	}

	/**
	 * Updates the sample l a r booking in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param sampleLARBooking the sample l a r booking
	 * @return the sample l a r booking that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SampleLARBooking updateSampleLARBooking(
		SampleLARBooking sampleLARBooking) {
		return sampleLARBookingPersistence.update(sampleLARBooking);
	}

	/**
	 * Returns the sample l a r booking local service.
	 *
	 * @return the sample l a r booking local service
	 */
	public SampleLARBookingLocalService getSampleLARBookingLocalService() {
		return sampleLARBookingLocalService;
	}

	/**
	 * Sets the sample l a r booking local service.
	 *
	 * @param sampleLARBookingLocalService the sample l a r booking local service
	 */
	public void setSampleLARBookingLocalService(
		SampleLARBookingLocalService sampleLARBookingLocalService) {
		this.sampleLARBookingLocalService = sampleLARBookingLocalService;
	}

	/**
	 * Returns the sample l a r booking persistence.
	 *
	 * @return the sample l a r booking persistence
	 */
	public SampleLARBookingPersistence getSampleLARBookingPersistence() {
		return sampleLARBookingPersistence;
	}

	/**
	 * Sets the sample l a r booking persistence.
	 *
	 * @param sampleLARBookingPersistence the sample l a r booking persistence
	 */
	public void setSampleLARBookingPersistence(
		SampleLARBookingPersistence sampleLARBookingPersistence) {
		this.sampleLARBookingPersistence = sampleLARBookingPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name remote service.
	 *
	 * @return the class name remote service
	 */
	public com.liferay.portal.service.ClassNameService getClassNameService() {
		return classNameService;
	}

	/**
	 * Sets the class name remote service.
	 *
	 * @param classNameService the class name remote service
	 */
	public void setClassNameService(
		com.liferay.portal.service.ClassNameService classNameService) {
		this.classNameService = classNameService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();

		PersistedModelLocalServiceRegistryUtil.register("com.liferay.samplelar.model.SampleLARBooking",
			sampleLARBookingLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"com.liferay.samplelar.model.SampleLARBooking");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return SampleLARBookingLocalService.class.getName();
	}

	@Override
	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != _classLoader) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected Class<?> getModelClass() {
		return SampleLARBooking.class;
	}

	protected String getModelClassName() {
		return SampleLARBooking.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = sampleLARBookingPersistence.getDataSource();

			DB db = DBFactoryUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.samplelar.service.SampleLARBookingLocalService.class)
	protected SampleLARBookingLocalService sampleLARBookingLocalService;
	@BeanReference(type = SampleLARBookingPersistence.class)
	protected SampleLARBookingPersistence sampleLARBookingPersistence;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameLocalService.class)
	protected com.liferay.portal.service.ClassNameLocalService classNameLocalService;
	@BeanReference(type = com.liferay.portal.service.ClassNameService.class)
	protected com.liferay.portal.service.ClassNameService classNameService;
	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private ClassLoader _classLoader;
	private SampleLARBookingLocalServiceClpInvoker _clpInvoker = new SampleLARBookingLocalServiceClpInvoker();
}