package com.dnb.loanservice.utils;

import java.time.LocalDate;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.hibernate.type.spi.TypeConfiguration;

public class CustomIdGenerator extends SequenceStyleGenerator {

	private static final long serialVersionUID = 1L;

	public static final String VALUE_PREFIX_PARAMETER = "valuePrefix";
	public static final String VALUE_PREFIX_DEFAULT = "";
	private String valuePrefix;

	public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
	public static final String NUMBER_FORMAT_DEFAULT = "%d";
	private String numberFormat;

	public static final String INCLUDE_DATE_PARAMETER = "includeDate";
	public static final boolean INCLUDE_DATE_DEFAULT = false;
	private boolean includeDate;

	public static final String DATE_FORMAT_PARAMETER = "dateFormat";
	public static final String DATE_FORMAT_DEFAULT = "%tY-%tm";

	public static final String DATE_NUMBER_SEPARATOR_PARAMETER = "dateNumberSeparator";
	public static final String DATE_NUMBER_SEPARATOR_DEFAULT = "_";

	private String format;

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

		if (includeDate)
			return String.format(format, LocalDate.now(), super.generate(session, object));
		else
			return String.format(format, super.generate(session, object));
	}

	@Override
	public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) throws MappingException {
		super.configure(new TypeConfiguration().getBasicTypeRegistry().getRegisteredType(Long.class), parameters,
				serviceRegistry);
		valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER, parameters, VALUE_PREFIX_DEFAULT);

		includeDate = ConfigurationHelper.getBooleanWrapper(INCLUDE_DATE_PARAMETER, parameters, INCLUDE_DATE_DEFAULT);

		if (includeDate) {
			String dateFormat = ConfigurationHelper.getString(DATE_FORMAT_PARAMETER, parameters, DATE_FORMAT_DEFAULT)
					.replace("%", "%1$");
			String dateNumberSeparator = ConfigurationHelper.getString(DATE_NUMBER_SEPARATOR_PARAMETER, parameters,
					DATE_NUMBER_SEPARATOR_DEFAULT);
			numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, parameters, NUMBER_FORMAT_DEFAULT)
					.replace("%", "%2$");
			this.format = valuePrefix + dateFormat + dateNumberSeparator + numberFormat;

		} else {
			numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, parameters, NUMBER_FORMAT_DEFAULT);
			this.format = valuePrefix + numberFormat;

		}

	}

}
