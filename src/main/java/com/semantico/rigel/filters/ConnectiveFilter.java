package com.semantico.rigel.filters;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.semantico.rigel.fields.Field;

//rename SymmetricConnectiveFilter ?
public abstract class ConnectiveFilter extends Filter {

    protected abstract List<Filter> getJoinedFilters();

    @Override
    public Set<Field<?>> getAffectedFields() {
        ImmutableSet.Builder<Field<?>> builder = ImmutableSet.builder();

        for (Filter filter : getJoinedFilters()) {
            builder.addAll(filter.getAffectedFields());
        }
        return builder.build();
    }

    protected List<String> getSolrFilters() {
        List<String> solrFilters = Lists.newArrayList();

        for (Filter filter : getJoinedFilters()) {
            String solrFilter = filter.toSolrFormat();
            if (StringUtils.isNotBlank(solrFilter)) {//isnt just whitespace
                solrFilters.add(solrFilter);
            }
        }
        return solrFilters;
    }
}
